package com.facility.management.usecases.wastage;

import com.facility.management.helpers.common.calc.DateTimeCalc;
import com.facility.management.persistence.models.*;
import com.facility.management.usecases.wastage.dto.InorganicProductDTO;
import com.facility.management.usecases.wastage.dto.InorganicWastageRequestDTO;
import com.facility.management.usecases.wastage.dto.OrganicWastageRequestDTO;
import com.facility.management.usecases.wastage.dao.WastageDao;
import com.facility.management.usecases.wastage.dto.*;
import com.facility.management.usecases.wastage.enums.MoveOWCType;
import com.facility.management.usecases.wastage.enums.ProjectInventoryProductType;
import com.facility.management.usecases.wastage.enums.WastageType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class WastageService {

    @Autowired
    WastageDao wastageDao;

    public Integer saveDailyWastage(String plant, List<AddWastageRequestDTO> wastageRequestDTOList) {
        Integer result = 0;
        try {
            List<DailyWastageDetailsDET> dailyWastageDetailsDETList = wastageDao.saveDailyWastageDet(plant, wastageRequestDTOList);

            for(DailyWastageDetailsDET dailyWastageDetailsDET: dailyWastageDetailsDETList) {
                boolean hasWastageType = wastageDao.checkTotalWastageType(plant, dailyWastageDetailsDET.getWastageType(), dailyWastageDetailsDET.getProjectNo());
                if(hasWastageType) {
                    DailyWastageDetailsHDR existingDailyWastageDetailsHdr = wastageDao.getDailyWastageDetailsHDR(plant, dailyWastageDetailsDET.getWastageType(), dailyWastageDetailsDET.getProjectNo());

                    existingDailyWastageDetailsHdr.setTotalWastageQty(existingDailyWastageDetailsHdr.getTotalWastageQty() + dailyWastageDetailsDET.getWastageQty());
                    existingDailyWastageDetailsHdr.setPendingQty(existingDailyWastageDetailsHdr.getPendingQty() + dailyWastageDetailsDET.getWastageQty());

                    result = wastageDao.updateDailyWastageHdr(dailyWastageDetailsDET.getPlant(), existingDailyWastageDetailsHdr);

                }
                else {
                    DailyWastageDetailsHDR dailyWastageDetailsHDR = DailyWastageDetailsHDR.builder()
                            .plant(dailyWastageDetailsDET.getPlant())
                            .totalWastageType(dailyWastageDetailsDET.getWastageType())
                            .totalWastageQty(dailyWastageDetailsDET.getWastageQty())
                            .totalWastageUOM(dailyWastageDetailsDET.getWastageUOM())
                            .projectNo(dailyWastageDetailsDET.getProjectNo())
                            .processedQty(0.0)
                            .pendingQty(dailyWastageDetailsDET.getWastageQty())
                            .build();

                    result = wastageDao.saveDailyWastageHdr(dailyWastageDetailsHDR.getPlant(), dailyWastageDetailsHDR);
                }
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        return result;
    }

    public List<WastageDetailsDTO> getWastageDetails(String plant, String projectNo, WastageType wastageType) {
        List<WastageDetailsDTO> wastageDetailsDTOList = null;
        try {
            wastageDetailsDTOList = wastageDao.getWastageDetails(plant, projectNo, wastageType);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        return wastageDetailsDTOList;
    }

    public HashMap<String, Integer> saveProcessedOrganicWastage(String plant, OrganicWastageRequestDTO organicWastageRequestDTO) {
        HashMap<String, Integer> result = new HashMap<>();
        try {
            DailyWastageDetailsHDR oldDailyWastageDetailsHDR = wastageDao.getDailyWastageDetailsHDR(plant, WastageType.ORGANIC_WASTE.toString(), organicWastageRequestDTO.getProjectNo()); //organic waste is hardcoded
            oldDailyWastageDetailsHDR.setPendingQty(oldDailyWastageDetailsHDR.getPendingQty() - organicWastageRequestDTO.getOrganicWastageQty());
            oldDailyWastageDetailsHDR.setProcessedQty(oldDailyWastageDetailsHDR.getProcessedQty() + organicWastageRequestDTO.getOrganicWastageQty() - organicWastageRequestDTO.getRejectedWastageQty());

            Integer dailyWastageHdrUpdated = wastageDao.updateDailyWastageHdr(plant, oldDailyWastageDetailsHDR);
            result.put("dailyWastageHdrUpdated", dailyWastageHdrUpdated);

            DateTimeCalc dateTimeCalc = new DateTimeCalc();
            OrganicWasteDET organicWasteDET = OrganicWasteDET.builder()
                    .plant(plant)
                    .projectNo(organicWastageRequestDTO.getProjectNo())
                    .date(dateTimeCalc.getTodayDMYDate())
                    .qty(organicWastageRequestDTO.getProcessedOrganicWastageQty())
                    .uom(organicWastageRequestDTO.getProcessedOrganicWastageUOM())
                    .empCode(organicWastageRequestDTO.getEmpCode())
                    .crAt(dateTimeCalc.getTodayDateTime())
                    .crBy(null)
                    .build();

            Integer organicWasteDETInserted = wastageDao.saveOrganicWastageDet(plant, organicWasteDET);
            result.put("organicWasteDETInserted", organicWasteDETInserted);

            boolean isOrganicWasteHDRExists = wastageDao.checkOrganicWasteHDR(plant, organicWastageRequestDTO.getProjectNo());

            if(isOrganicWasteHDRExists) {
                OrganicWasteHDR existingOrganicWasteHDR = wastageDao.getOrganicWasteHDR(plant, organicWastageRequestDTO.getProjectNo());
                existingOrganicWasteHDR.setTotalQty(existingOrganicWasteHDR.getTotalQty() + organicWastageRequestDTO.getProcessedOrganicWastageQty());
                existingOrganicWasteHDR.setPendingQty(existingOrganicWasteHDR.getPendingQty() + organicWastageRequestDTO.getProcessedOrganicWastageQty());

                Integer organicWasteHdrUpdated = wastageDao.updateOrganicWasteHdr(plant, existingOrganicWasteHDR);
                result.put("organicWasteHdrUpdated", organicWasteHdrUpdated);

            } else {
                OrganicWasteHDR organicWasteHDR = OrganicWasteHDR.builder()
                        .plant(plant)
                        .projectNo(organicWastageRequestDTO.getProjectNo())
                        .totalQty(organicWastageRequestDTO.getProcessedOrganicWastageQty())
                        .totalUOM(organicWastageRequestDTO.getProcessedOrganicWastageUOM())
                        .processedQty(0.0)
                        .pendingQty(organicWastageRequestDTO.getProcessedOrganicWastageQty())
                        .build();

                Integer organicWasteHdrInserted = wastageDao.saveOrganicWastageHdr(plant, organicWasteHDR);
                result.put("organicWasteHdrInserted", organicWasteHdrInserted);
            }

            RejectedWasteDET rejectedWasteDET = RejectedWasteDET.builder()
                    .plant(plant)
                    .projectNo(organicWastageRequestDTO.getProjectNo())
                    .date(dateTimeCalc.getTodayDMYDate())
                    .qty(organicWastageRequestDTO.getRejectedWastageQty())
                    .uom(organicWastageRequestDTO.getRejectedWastageUOM())
                    .empCode(organicWastageRequestDTO.getEmpCode())
                    .wastageType(WastageType.ORGANIC_WASTE.toString())
                    .build();

            Integer rejectedWasteDETInserted = wastageDao.saveRejectedWasteDet(plant, rejectedWasteDET);
            result.put("rejectedWasteDETInserted", rejectedWasteDETInserted);

            boolean hasRejectedWastageType = wastageDao.checkTotalWastageType(plant, WastageType.REJECTED_WASTE.name(), organicWastageRequestDTO.getProjectNo());

            if(hasRejectedWastageType) {
                DailyWastageDetailsHDR existingDailyWastageDetailsHdr = wastageDao.getDailyWastageDetailsHDR(plant, WastageType.REJECTED_WASTE.name(), organicWastageRequestDTO.getProjectNo());

                existingDailyWastageDetailsHdr.setTotalWastageQty(existingDailyWastageDetailsHdr.getTotalWastageQty() + organicWastageRequestDTO.getRejectedWastageQty());
                existingDailyWastageDetailsHdr.setPendingQty(existingDailyWastageDetailsHdr.getPendingQty() + organicWastageRequestDTO.getRejectedWastageQty());

                Integer rejectedWasteUpdatedInDailyWastage = wastageDao.updateDailyWastageHdr(plant, existingDailyWastageDetailsHdr);
                result.put("rejectedWasteUpdatedInDailyWastage", rejectedWasteUpdatedInDailyWastage);
            } else {
                DailyWastageDetailsHDR dailyWastageDetailsHDR = DailyWastageDetailsHDR.builder()
                        .plant(plant)
                        .totalWastageType(WastageType.REJECTED_WASTE.name())
                        .totalWastageQty(organicWastageRequestDTO.getRejectedWastageQty())
                        .totalWastageUOM(organicWastageRequestDTO.getRejectedWastageUOM())
                        .projectNo(organicWastageRequestDTO.getProjectNo())
                        .processedQty(0.0)
                        .pendingQty(organicWastageRequestDTO.getRejectedWastageQty())
                        .build();

                Integer rejectedWasteInsertedInDailyWastage = wastageDao.saveDailyWastageHdr(dailyWastageDetailsHDR.getPlant(), dailyWastageDetailsHDR);
                result.put("rejectedWasteInsertedInDailyWastage", rejectedWasteInsertedInDailyWastage);
            }

            boolean isRejectedWasteHdrExists = wastageDao.checkRejectedWasteHDR(plant, organicWastageRequestDTO.getProjectNo());

            if(isRejectedWasteHdrExists) {
                RejectedWasteHDR existingRejectedWasteHDR = wastageDao.getRejectedWasteHDR(plant, organicWastageRequestDTO.getProjectNo());
                existingRejectedWasteHDR.setTotalQty(existingRejectedWasteHDR.getTotalQty() + organicWastageRequestDTO.getRejectedWastageQty());
                existingRejectedWasteHDR.setPendingQty(existingRejectedWasteHDR.getPendingQty() + organicWastageRequestDTO.getRejectedWastageQty());

                Integer rejectedWasteHdrUpdated = wastageDao.updateRejectedWasteHdr(plant, existingRejectedWasteHDR);
                result.put("rejectedWasteHdrUpdated", rejectedWasteHdrUpdated);

            } else {
                RejectedWasteHDR rejectedWasteHDR = RejectedWasteHDR.builder()
                        .plant(plant)
                        .projectNo(organicWastageRequestDTO.getProjectNo())
                        .totalQty(organicWastageRequestDTO.getRejectedWastageQty())
                        .totalUOM(organicWastageRequestDTO.getRejectedWastageUOM())
                        .processedQty(0.0)
                        .pendingQty(organicWastageRequestDTO.getRejectedWastageQty())
                        .build();

                Integer rejectedWasteHdrInserted = wastageDao.saveRejectedWasteHdr(plant, rejectedWasteHDR);
                result.put("rejectedWasteHdrInserted", rejectedWasteHdrInserted);
            }

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        return result;
    }

    public HashMap<String, Integer> saveInorganicWastage(String plant, InorganicWastageRequestDTO inorganicWastageRequestDTO) {
        HashMap<String, Integer> result = new HashMap<>();
        try {
            DailyWastageDetailsHDR oldDailyWastageDetailsHDR = wastageDao.getDailyWastageDetailsHDR(plant, WastageType.INORGANIC_WASTE.toString(), inorganicWastageRequestDTO.getProjectNo()); //inorganic waste is hardcoded
            oldDailyWastageDetailsHDR.setPendingQty(oldDailyWastageDetailsHDR.getPendingQty() - inorganicWastageRequestDTO.getInorganicWastageQty());
            oldDailyWastageDetailsHDR.setProcessedQty(oldDailyWastageDetailsHDR.getProcessedQty() + inorganicWastageRequestDTO.getInorganicWastageQty() - inorganicWastageRequestDTO.getRejectedWastageQty());

            Integer dailyWastageHdrUpdated = wastageDao.updateDailyWastageHdr(plant, oldDailyWastageDetailsHDR);
            result.put("dailyWastageHdrUpdated", dailyWastageHdrUpdated);

            DateTimeCalc dateTimeCalc = new DateTimeCalc();

            for(InorganicProductDTO inorganicProductDTO: inorganicWastageRequestDTO.getInorganicProductDTOList()) {
                InorganicWasteDET inorganicWasteDET = InorganicWasteDET.builder()
                        .plant(plant)
                        .projectNo(inorganicWastageRequestDTO.getProjectNo())
                        .date(dateTimeCalc.getTodayDMYDate())
                        .item(inorganicProductDTO.getItem())
                        .qty(inorganicProductDTO.getQty())
                        .uom(inorganicProductDTO.getUom())
                        .empCode(inorganicWastageRequestDTO.getEmpCode())
                        .build();

                Integer inorganicWasteDETInserted = wastageDao.saveInorganicWastageDet(plant, inorganicWasteDET);
                result.put("inorganicWasteDETInserted - " + inorganicWasteDET.getItem(), inorganicWasteDETInserted);
            }

            for(InorganicProductDTO inorganicProductDTO: inorganicWastageRequestDTO.getInorganicProductDTOList()) {
                boolean isInorganicWasteProductExists = wastageDao.checkInorganicWasteProduct(plant, inorganicWastageRequestDTO.getProjectNo(), inorganicProductDTO.getItem());

                if(isInorganicWasteProductExists) {
                    InorganicWasteProductDET existingInorganicWasteProductDET = wastageDao.getInorganicWasteProductDET(plant, inorganicWastageRequestDTO.getProjectNo(), inorganicProductDTO.getItem());
                    existingInorganicWasteProductDET.setQty(existingInorganicWasteProductDET.getQty() + inorganicProductDTO.getQty());
                    existingInorganicWasteProductDET.setPendingQty(existingInorganicWasteProductDET.getPendingQty() + inorganicProductDTO.getQty());

                    Integer inorganicWasteProductDETUpdated = wastageDao.updateInorganicWasteProductDet(plant, existingInorganicWasteProductDET);

                    result.put("inorganicWasteProductDETUpdated - " + inorganicProductDTO.getItem(), inorganicWasteProductDETUpdated);
                } else {
                    InorganicWasteProductDET inorganicWasteProductDET = InorganicWasteProductDET.builder()
                            .plant(plant)
                            .projectNo(inorganicWastageRequestDTO.getProjectNo())
                            .item(inorganicProductDTO.getItem())
                            .qty(inorganicProductDTO.getQty())
                            .uom(inorganicProductDTO.getUom())
                            .processedQty(0.0)
                            .pendingQty(inorganicProductDTO.getQty())
                            .build();

                    Integer inorganicWasteProductDETInserted = wastageDao.saveInorganicWasteProductDet(plant, inorganicWasteProductDET);
                    result.put("inorganicWasteProductDETInserted - " + inorganicProductDTO.getItem(), inorganicWasteProductDETInserted);
                }

                boolean isProjectInventoryExists = wastageDao.checkProjectInventory(plant, inorganicWastageRequestDTO.getProjectNo(), inorganicProductDTO.getItem());

                if(isProjectInventoryExists) {
                    ProjectInventory existingProjectInventory = wastageDao.getProjectInventory(plant, inorganicWastageRequestDTO.getProjectNo(), inorganicProductDTO.getItem());

                    existingProjectInventory.setTotalQty(existingProjectInventory.getTotalQty() + inorganicProductDTO.getQty());
                    existingProjectInventory.setPendingQty(existingProjectInventory.getPendingQty() + inorganicProductDTO.getQty());

                    Integer projectInventoryUpdated = wastageDao.updateProjectInventory(plant, existingProjectInventory, inorganicProductDTO.getItem());
                    result.put("projectInventoryUpdated", projectInventoryUpdated);
                } else {
                    ProjectInventory projectInventory = ProjectInventory.builder()
                            .plant(plant)
                            .projectNo(inorganicWastageRequestDTO.getProjectNo())
                            .totalQty(inorganicProductDTO.getQty())
                            .uom(inorganicProductDTO.getUom())
                            .processedQty(0.0)
                            .pendingQty(inorganicProductDTO.getQty())
                            .build();

                    Integer projectInventoryInserted = wastageDao.saveProjectInventory(plant, projectInventory, inorganicProductDTO.getItem());
                    result.put("projectInventoryInserted", projectInventoryInserted);
                }
            }

            boolean isInorganicWastageHdrExists = wastageDao.checkInorganicWasteHDR(plant, inorganicWastageRequestDTO.getProjectNo());

            if(isInorganicWastageHdrExists) {
                InorganicWasteHDR existingInorganicWasteHDR = wastageDao.getInorganicWasteHDR(plant, inorganicWastageRequestDTO.getProjectNo());
                existingInorganicWasteHDR.setTotalQty(existingInorganicWasteHDR.getTotalQty() + (inorganicWastageRequestDTO.getInorganicWastageQty() - inorganicWastageRequestDTO.getRejectedWastageQty()));
                existingInorganicWasteHDR.setPendingQty(existingInorganicWasteHDR.getPendingQty() + (inorganicWastageRequestDTO.getInorganicWastageQty() - inorganicWastageRequestDTO.getRejectedWastageQty()));

                Integer inorganicWasteHDRUpdated = wastageDao.updateInorganicWasteHdr(plant, existingInorganicWasteHDR);
                result.put("inorganicWasteHDRUpdated", inorganicWasteHDRUpdated);
            } else {
                InorganicWasteHDR inorganicWasteHDR = InorganicWasteHDR.builder()
                        .plant(plant)
                        .projectNo(inorganicWastageRequestDTO.getProjectNo())
                        .totalQty(inorganicWastageRequestDTO.getInorganicWastageQty() - inorganicWastageRequestDTO.getRejectedWastageQty())
                        .totalUOM(inorganicWastageRequestDTO.getInorganicWastageUOM())
                        .processedQty(0.0)
                        .pendingQty(inorganicWastageRequestDTO.getInorganicWastageQty() - inorganicWastageRequestDTO.getRejectedWastageQty())
                        .build();

                Integer inorganicWasteHDRInserted = wastageDao.saveInorganicWasteHdr(plant, inorganicWasteHDR);
                result.put("inorganicWasteHDRInserted", inorganicWasteHDRInserted);
            }

            RejectedWasteDET rejectedWasteDET = RejectedWasteDET.builder()
                    .plant(plant)
                    .projectNo(inorganicWastageRequestDTO.getProjectNo())
                    .date(dateTimeCalc.getTodayDMYDate())
                    .qty(inorganicWastageRequestDTO.getRejectedWastageQty())
                    .uom(inorganicWastageRequestDTO.getRejectedWastageUOM())
                    .empCode(inorganicWastageRequestDTO.getEmpCode())
                    .wastageType(WastageType.INORGANIC_WASTE.toString())
                    .build();

            Integer rejectedWasteDETInserted = wastageDao.saveRejectedWasteDet(plant, rejectedWasteDET);
            result.put("rejectedWasteDETInserted", rejectedWasteDETInserted);

            boolean hasRejectedWastageType = wastageDao.checkTotalWastageType(plant, WastageType.REJECTED_WASTE.name(), inorganicWastageRequestDTO.getProjectNo());

            if(hasRejectedWastageType) {
                DailyWastageDetailsHDR existingDailyWastageDetailsHdr = wastageDao.getDailyWastageDetailsHDR(plant, WastageType.REJECTED_WASTE.name(), inorganicWastageRequestDTO.getProjectNo());

                existingDailyWastageDetailsHdr.setTotalWastageQty(existingDailyWastageDetailsHdr.getTotalWastageQty() + inorganicWastageRequestDTO.getRejectedWastageQty());
                existingDailyWastageDetailsHdr.setPendingQty(existingDailyWastageDetailsHdr.getPendingQty() + inorganicWastageRequestDTO.getRejectedWastageQty());

                Integer rejectedWasteUpdatedInDailyWastage = wastageDao.updateDailyWastageHdr(plant, existingDailyWastageDetailsHdr);
                result.put("rejectedWasteUpdatedInDailyWastage", rejectedWasteUpdatedInDailyWastage);
            } else {
                DailyWastageDetailsHDR dailyWastageDetailsHDR = DailyWastageDetailsHDR.builder()
                        .plant(plant)
                        .totalWastageType(WastageType.REJECTED_WASTE.name())
                        .totalWastageQty(inorganicWastageRequestDTO.getRejectedWastageQty())
                        .totalWastageUOM(inorganicWastageRequestDTO.getRejectedWastageUOM())
                        .projectNo(inorganicWastageRequestDTO.getProjectNo())
                        .processedQty(0.0)
                        .pendingQty(inorganicWastageRequestDTO.getRejectedWastageQty())
                        .build();

                Integer rejectedWasteInsertedInDailyWastage = wastageDao.saveDailyWastageHdr(dailyWastageDetailsHDR.getPlant(), dailyWastageDetailsHDR);
                result.put("rejectedWasteInsertedInDailyWastage", rejectedWasteInsertedInDailyWastage);
            }



            boolean isRejectedWasteHdrExists = wastageDao.checkRejectedWasteHDR(plant, inorganicWastageRequestDTO.getProjectNo());

            if(isRejectedWasteHdrExists) {
                RejectedWasteHDR existingRejectedWasteHDR = wastageDao.getRejectedWasteHDR(plant, inorganicWastageRequestDTO.getProjectNo());
                existingRejectedWasteHDR.setTotalQty(existingRejectedWasteHDR.getTotalQty() + inorganicWastageRequestDTO.getRejectedWastageQty());
                existingRejectedWasteHDR.setPendingQty(existingRejectedWasteHDR.getPendingQty() + inorganicWastageRequestDTO.getRejectedWastageQty());

                Integer rejectedWasteHdrUpdated = wastageDao.updateRejectedWasteHdr(plant, existingRejectedWasteHDR);
                result.put("rejectedWasteHdrUpdated", rejectedWasteHdrUpdated);

            } else {
                RejectedWasteHDR rejectedWasteHDR = RejectedWasteHDR.builder()
                        .plant(plant)
                        .projectNo(inorganicWastageRequestDTO.getProjectNo())
                        .totalQty(inorganicWastageRequestDTO.getRejectedWastageQty())
                        .totalUOM(inorganicWastageRequestDTO.getRejectedWastageUOM())
                        .processedQty(0.0)
                        .pendingQty(inorganicWastageRequestDTO.getRejectedWastageQty())
                        .build();

                Integer rejectedWasteHdrInserted = wastageDao.saveRejectedWasteHdr(plant, rejectedWasteHDR);
                result.put("rejectedWasteHdrInserted", rejectedWasteHdrInserted);
            }

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        return result;
    }

    public HashMap<String, Integer> sendToBioGas(String plant, BioGasRequestDTO bioGasRequestDTO){
        HashMap<String, Integer> result = new HashMap<>();
        try {
            DateTimeCalc dateTimeCalc = new DateTimeCalc();
            BioGasDET bioGasDET = BioGasDET.builder()
                    .plant(plant)
                    .projectNo(bioGasRequestDTO.getProjectNo())
                    .date(dateTimeCalc.getTodayDMYDate())
                    .qty(bioGasRequestDTO.getQty())
                    .uom(bioGasRequestDTO.getUom())
                    .empCode(bioGasRequestDTO.getEmpCode())
                    .build();
            Integer bioGasDetInserted = wastageDao.saveBioGasDet(plant, bioGasDET);
            result.put("bioGasDetInserted", bioGasDetInserted);

            boolean isBioGasHdrExists = wastageDao.checkBioGasHDR(plant, bioGasRequestDTO.getProjectNo());

            if(isBioGasHdrExists) {
                BioGasHDR existingBioGasHDR = wastageDao.getBioGasHDR(plant, bioGasRequestDTO.getProjectNo());

                existingBioGasHDR.setTotalQty(existingBioGasHDR.getTotalQty() + bioGasRequestDTO.getQty());
                existingBioGasHDR.setPendingQty(existingBioGasHDR.getPendingQty() + bioGasRequestDTO.getQty());

                Integer bioGasHdrUpdated = wastageDao.updateBioGasHdr(plant, existingBioGasHDR);
                result.put("bioGasHdrUpdated", bioGasHdrUpdated);

            } else {
                BioGasHDR bioGasHDR = BioGasHDR.builder()
                        .plant(plant)
                        .projectNo(bioGasDET.getProjectNo())
                        .totalQty(bioGasRequestDTO.getQty())
                        .totalUOM(bioGasDET.getUom())
                        .processedQty(0.0)
                        .pendingQty(bioGasRequestDTO.getQty())
                        .build();

                Integer bioGasHdrInserted = wastageDao.saveBioGasHdr(plant, bioGasHDR);
                result.put("bioGasHdrInserted", bioGasHdrInserted);
            }

            boolean isProjectInventoryExists = wastageDao.checkProjectInventory(plant, bioGasRequestDTO.getProjectNo(), ProjectInventoryProductType.BIOGAS.name());

            if(isProjectInventoryExists) {
                ProjectInventory existingProjectInventory = wastageDao.getProjectInventory(plant, bioGasRequestDTO.getProjectNo(), ProjectInventoryProductType.BIOGAS.name());

                existingProjectInventory.setTotalQty(existingProjectInventory.getTotalQty() + bioGasRequestDTO.getQty());
                existingProjectInventory.setPendingQty(existingProjectInventory.getPendingQty() + bioGasRequestDTO.getQty());

                Integer projectInventoryUpdated = wastageDao.updateProjectInventory(plant, existingProjectInventory, ProjectInventoryProductType.BIOGAS.name());
                result.put("projectInventoryUpdated", projectInventoryUpdated);
            } else {
                ProjectInventory projectInventory = ProjectInventory.builder()
                        .plant(plant)
                        .projectNo(bioGasDET.getProjectNo())
                        .totalQty(bioGasRequestDTO.getQty())
                        .uom(bioGasDET.getUom())
                        .processedQty(0.0)
                        .pendingQty(bioGasRequestDTO.getQty())
                        .build();

                Integer projectInventoryInserted = wastageDao.saveProjectInventory(plant, projectInventory, ProjectInventoryProductType.BIOGAS.name());
                result.put("projectInventoryInserted", projectInventoryInserted);
            }

            DailyWastageDetailsHDR oldDailyWastageDetailsHDR = wastageDao.getDailyWastageDetailsHDR(plant, WastageType.ORGANIC_WASTE.toString(), bioGasRequestDTO.getProjectNo()); //inorganic waste is hardcoded
            oldDailyWastageDetailsHDR.setProcessedQty(oldDailyWastageDetailsHDR.getProcessedQty() - bioGasRequestDTO.getQty());

            Integer dailyWastageHdrUpdated = wastageDao.updateDailyWastageHdr(plant, oldDailyWastageDetailsHDR);
            result.put("dailyWastageHdrUpdated", dailyWastageHdrUpdated);


        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        return result;
    }

    public HashMap<String, Integer> sendToOWCMachine(String plant, OWCMachineRequestDTO owcMachineRequestDTO) {
        HashMap<String, Integer> result = new HashMap<>();
        try {
            DateTimeCalc dateTimeCalc = new DateTimeCalc();

            for(OWCMachineProduct owcMachineProduct: owcMachineRequestDTO.getOwcMachineProducts()) {
                boolean isOWCHDRExists = wastageDao.checkOWCHDR(plant, owcMachineRequestDTO.getProjectNo());

                if (isOWCHDRExists) { //Want to change logic if the requested uom do not contain KG
                    OWCHDR existingOWCHdr = wastageDao.getOWCHDR(plant, owcMachineRequestDTO.getProjectNo());

//                    double productsQty = 0.0;
//
//                    for(OWCProductDTO owcProductDTO : owcMachineProduct.getOwcMachineProductDTOList()) {
//                        productsQty = productsQty + owcProductDTO.getQty();
//                    }

                    existingOWCHdr.setTotalQty(existingOWCHdr.getTotalQty() + owcMachineProduct.getQty());

                    Integer owcHdrUpdated = wastageDao.updateOWCHdr(plant, existingOWCHdr);
                    result.put("owcHdrUpdated - " + owcMachineProduct.getMachineId(), owcHdrUpdated);

                } else {
//                    double productsQty = 0.0;
//                    String uom = "";
//
//                    for(OWCProductDTO owcProductDTO : owcMachineProduct.getOwcMachineProductDTOList()) {
//                        productsQty = productsQty + owcProductDTO.getQty();
//                        uom = owcProductDTO.getUom();
//                    }

                    OWCHDR owcHdr = OWCHDR.builder()
                            .plant(plant)
                            .projectNo(owcMachineRequestDTO.getProjectNo())
                            .totalQty(owcMachineProduct.getQty())
                            .totalUOM(owcMachineProduct.getUom())
                            .build();
                    Integer hdrId = wastageDao.saveOWCHdr(plant, owcHdr);

                    if(hdrId > 1) {
                        Integer owcHdrInserted = 1;
                        result.put("owcHdrInserted - " + owcHdrInserted, owcHdrInserted);
                    }

                    for(OWCMachineProduct owcMachineProduct1: owcMachineRequestDTO.getOwcMachineProducts()) {


                        OWCDET owcDet = OWCDET.builder()
                                .plant(plant)
                                .projectNo(owcMachineRequestDTO.getProjectNo())
                                .date(dateTimeCalc.getTodayDMYDate())
                                .qty(owcMachineProduct1.getQty())
                                .uom(owcMachineProduct1.getUom())
                                .empCode(owcMachineRequestDTO.getEmpCode())
                                .machineId(owcMachineProduct1.getMachineId())
                                .build();

                        Integer detId = wastageDao.saveOWCDet(plant, owcDet);

                        if(detId > 1) {
                            Integer owcDetInserted = 1;
                            result.put("owcDetInserted - " + owcMachineProduct1.getMachineId(), owcDetInserted);
                        }

                        for(OWCProductDTO owcProductDTO: owcMachineProduct.getOwcMachineProductDTOList()) {
                            OWCProductDET owcProductDET = OWCProductDET.builder()
                                    .plant(plant)
                                    .projectNo(owcMachineRequestDTO.getProjectNo())
                                    .detId(detId)
                                    .hdrId(hdrId)
                                    .product(owcProductDTO.getItem())
                                    .qty(owcProductDTO.getQty())
                                    .uom(owcProductDTO.getUom())
                                    .build();

                            Integer owcProductDETInserted = wastageDao.saveOWCProductDET(plant, owcProductDET);
                            result.put("owcProductDETInserted", owcProductDETInserted);
                        }

                    }

                }

//                double productsQty1 = 0.0;
//
//                for(OWCProductDTO owcProductDTO : owcMachineProduct.getOwcMachineProductDTOList()) {
//                    productsQty1 = productsQty1 + owcProductDTO.getQty();
//                }

                DailyWastageDetailsHDR oldDailyWastageDetailsHDR = wastageDao.getDailyWastageDetailsHDR(plant, WastageType.ORGANIC_WASTE.toString(), owcMachineRequestDTO.getProjectNo()); //organic waste is hardcoded
                oldDailyWastageDetailsHDR.setProcessedQty(oldDailyWastageDetailsHDR.getProcessedQty() - owcMachineProduct.getQty());

                Integer dailyWastageHdrUpdated = wastageDao.updateDailyWastageHdr(plant, oldDailyWastageDetailsHDR);
                result.put("dailyWastageHdrUpdated", dailyWastageHdrUpdated);

            }



//                boolean isProjectInventoryExists = wastageDao.checkProjectInventory(plant, owcMachineRequestDTO.getProjectNo(), ProjectInventoryProductType.OWC_OUTCOME.name());
//
//                if(isProjectInventoryExists) {
//                    ProjectInventory existingProjectInventory = wastageDao.getProjectInventory(plant, owcMachineRequestDTO.getProjectNo(), ProjectInventoryProductType.OWC_OUTCOME.name());
//
//                    existingProjectInventory.setTotalQty(existingProjectInventory.getTotalQty() + owcMachineProduct.getQty());
//                    existingProjectInventory.setPendingQty(existingProjectInventory.getPendingQty() + owcMachineProduct.getQty());
//
//                    Integer projectInventoryUpdated = wastageDao.updateProjectInventory(plant, existingProjectInventory, ProjectInventoryProductType.BIOGAS.name());
//                    result.put("projectInventoryUpdated", projectInventoryUpdated);
//                } else {
//                    ProjectInventory projectInventory = ProjectInventory.builder()
//                            .plant(plant)
//                            .projectNo(owcMachineRequestDTO.getProjectNo())
//                            .totalQty(owcMachineProduct.getQty())
//                            .uom(owcMachineProduct.getUom())
//                            .processedQty(0.0)
//                            .pendingQty(owcMachineProduct.getQty())
//                            .build();
//
//                    Integer projectInventoryInserted = wastageDao.saveProjectInventory(plant, projectInventory, ProjectInventoryProductType.BIOGAS.name());
//                    result.put("projectInventoryInserted", projectInventoryInserted);
//                }






        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        return result;
    }

    public HashMap<String, Integer> receiveOWCOutcome(String plant, ReceivedOWCOutcomeRequest receivedOWCOutcomeRequest) {
        HashMap<String, Integer> result = new HashMap<>();
        try {
            DateTimeCalc dateTimeCalc = new DateTimeCalc();
            for(ReceivedOWCOutcomeProduct receivedOWCOutcomeProduct: receivedOWCOutcomeRequest.getReceivedOWCOutcomeProducts()) {
                OWCOutcomeDET owcOutcomeDET = OWCOutcomeDET.builder()
                        .plant(plant)
                        .projectNo(receivedOWCOutcomeRequest.getProjectNo())
                        .date(dateTimeCalc.getTodayDMYDate())
                        .empCode(receivedOWCOutcomeRequest.getEmpCode())
                        .machineId(receivedOWCOutcomeProduct.getMachineId())
                        .product(receivedOWCOutcomeProduct.getProduct())
                        .qty(receivedOWCOutcomeProduct.getQty())
                        .uom(receivedOWCOutcomeProduct.getUom())
                        .build();

                Integer owcOutcomeDetInserted = wastageDao.saveOWCOutcomeDet(plant, owcOutcomeDET);
                result.put("owcOutcomeDetInserted - " + receivedOWCOutcomeProduct.getMachineId(), owcOutcomeDetInserted);
            }

            for(ReceivedOWCOutcomeProduct receivedOWCOutcomeProduct: receivedOWCOutcomeRequest.getReceivedOWCOutcomeProducts()) {
                boolean isOWCHDRExists = wastageDao.checkOWCOutcomeHDR(plant, receivedOWCOutcomeRequest.getProjectNo());

                if(isOWCHDRExists) { //Want to change logic if the requested uom do not contain KG
                    OWCOutcomeHDR existingOWCOutcomeHdr = wastageDao.getOWCOutcomeHDR(plant, receivedOWCOutcomeRequest.getProjectNo());
                    existingOWCOutcomeHdr.setTotalQty(existingOWCOutcomeHdr.getTotalQty() + receivedOWCOutcomeProduct.getQty());

                    Integer owcOutcomeHdrUpdated = wastageDao.updateOWCOutcomeHdr(plant, existingOWCOutcomeHdr);
                    result.put("owcOutcomeHdrUpdated - " + receivedOWCOutcomeProduct.getMachineId(), owcOutcomeHdrUpdated);
                } else {
                    OWCOutcomeHDR owcOutcomeHdr = OWCOutcomeHDR.builder()
                            .plant(plant)
                            .projectNo(receivedOWCOutcomeRequest.getProjectNo())
                            .totalQty(receivedOWCOutcomeProduct.getQty())
                            .processedQty(0.0)
                            .pendingQty(receivedOWCOutcomeProduct.getQty())
                            .totalUOM(receivedOWCOutcomeProduct.getUom())
                            .build();
                    Integer owcOutcomeHdrInserted = wastageDao.saveOWCOutcomeHdr(plant, owcOutcomeHdr);
                    result.put("owcOutcomeHdrInserted - " + receivedOWCOutcomeProduct.getMachineId(), owcOutcomeHdrInserted);
                }
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        return result;
    }

    public List<BioGasDTO> getBioGasSummary(String plant, String projectNo) {
        List<BioGasDTO> bioGasDTOList = null;
        try {
            bioGasDTOList = wastageDao.getBioGasSummary(plant, projectNo);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        return bioGasDTOList;
    }

    public List<OWCMachineDTO> getOWCSummary(String plant, String projectNo) {
        List<OWCMachineDTO> owcMachineDTOList = null;
        try {
            owcMachineDTOList = wastageDao.getOWCSummary(plant, projectNo);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        return owcMachineDTOList;
    }

    public List<OWCOutcomeDTO> getReceivedOWCOutcomeSummary(String plant, String projectNo) {
        List<OWCOutcomeDTO> owcOutcomeDTOList = null;
        try {
            owcOutcomeDTOList = wastageDao.getReceivedOWCOutcomeSummary(plant, projectNo);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        return owcOutcomeDTOList;
    }

    public List<WastageDTO> getWastageSummary(String plant, String projectNo, String date) {
        List<WastageDTO> wastageDTOList = null;
        try {
            wastageDTOList = wastageDao.getWastageSummary(plant, projectNo, date);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        return wastageDTOList;
    }

    public List<OrganicWastageSummaryDTO> getOrganicWastageSummary(String plant, String projectNo) {
        List<OrganicWastageSummaryDTO> organicWastageSummaryDTOList = null;
        try {
            organicWastageSummaryDTOList = wastageDao.getOrganicWastageSummary(plant, projectNo);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        return organicWastageSummaryDTOList;
    }

    public List<RejectedWastageSummaryDTO> getRejectedWastageSummary(String plant, String projectNo) {
        List<RejectedWastageSummaryDTO> rejectedWastageSummaryDTOList = null;
        try {
            rejectedWastageSummaryDTOList = wastageDao.getRejectedWastageSummary(plant, projectNo);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return rejectedWastageSummaryDTOList;
    }

    public List<InorganicWastageSummaryDTO> getInorganicWastageSummary(String plant, String projectNo) {
        List<InorganicWastageSummaryDTO> inorganicWastageSummaryDTOList = null;
        try {
            inorganicWastageSummaryDTOList = wastageDao.getInorganicWastageSummary(plant, projectNo);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return inorganicWastageSummaryDTOList;
    }

    public List<OWCMachineMstDTO> getOWCMachines(String plant, String projectNo) {
        List<OWCMachineMstDTO> owcMachineMstDTOList = null;
        try {
            owcMachineMstDTOList = wastageDao.getOWCMachines(plant, projectNo);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        return owcMachineMstDTOList;
    }


    public HashMap<String, Integer> moveOWCOutcome(String plant, MoveOWCOutcomeRequest moveOWCOutcomeRequest) {
        HashMap<String, Integer> result = new HashMap<>();
        try {
            DateTimeCalc dateTimeCalc = new DateTimeCalc();
            for(MoveOWCOutcomeProductDTO moveOWCOutcomeProductDTO : moveOWCOutcomeRequest.getMoveOWCOutcomeProducts()) {
                if(moveOWCOutcomeProductDTO.getMoveOWCType() == MoveOWCType.INTERNAL) {
                    boolean isOWCOutcomeExists = wastageDao.checkProjectInventory(plant, moveOWCOutcomeRequest.getProjectNo(), moveOWCOutcomeProductDTO.getProduct());
                    if(isOWCOutcomeExists) {
                        ProjectInventory existingProjectInventory = wastageDao.getProjectInventory(plant, moveOWCOutcomeRequest.getProjectNo(), moveOWCOutcomeProductDTO.getProduct());

                        existingProjectInventory.setPendingQty(existingProjectInventory.getPendingQty() + moveOWCOutcomeProductDTO.getQty());
                        existingProjectInventory.setTotalQty(existingProjectInventory.getTotalQty() + moveOWCOutcomeProductDTO.getQty());

                        Integer projectInventoryUpdated = wastageDao.updateProjectInventory(plant, existingProjectInventory, moveOWCOutcomeProductDTO.getProduct());
                        result.put("projectInventoryUpdated", projectInventoryUpdated);

                    } else {
                        ProjectInventory projectInventory = ProjectInventory.builder()
                                .plant(plant)
                                .projectNo(moveOWCOutcomeRequest.getProjectNo())
                                .product(moveOWCOutcomeProductDTO.getProduct())
                                .totalQty(moveOWCOutcomeProductDTO.getQty())
                                .uom(moveOWCOutcomeProductDTO.getUom())
                                .processedQty(0.0)
                                .pendingQty(moveOWCOutcomeProductDTO.getQty())
                                .build();

                        //save project inventory
                        Integer projectInventoryInserted = wastageDao.saveProjectInventory(plant, projectInventory, moveOWCOutcomeProductDTO.getProduct());
                        result.put("projectInventoryInserted", projectInventoryInserted);
                    }


                } else {
                    ProjectExternalInventory projectExternalInventory = ProjectExternalInventory.builder()
                            .plant(plant)
                            .projectNo(moveOWCOutcomeRequest.getProjectNo())
                            .date(dateTimeCalc.getTodayDMYDate())
                            .qty(moveOWCOutcomeProductDTO.getQty())
                            .product(moveOWCOutcomeProductDTO.getProduct())
                            .uom(moveOWCOutcomeProductDTO.getUom())
                            .build();

                    //save project external inventory
                    Integer projectExternalInventoryInserted = wastageDao.saveProjectExternalInventory(plant, projectExternalInventory, moveOWCOutcomeProductDTO.getProduct());
                    result.put("projectExternalInventoryInserted", projectExternalInventoryInserted);

                }
            }

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        return result;
    }

    public List<OWCOutcomeProductDTO> getOWCOutcomeProducts(String plant, String projectNo) {
        List<OWCOutcomeProductDTO> owcOutcomeProductDTOList = null;
        try {
           owcOutcomeProductDTOList = wastageDao.getOWCOutcomeProducts(plant, projectNo);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        return owcOutcomeProductDTOList;
    }


    public List<ProjectInventoryDTO> getAllProjectInventory(String plant, String projectNo) {
        List<ProjectInventoryDTO> projectInventoryDTOList = null;
        try {
            projectInventoryDTOList = wastageDao.getAllProjectInventory(plant, projectNo);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        return projectInventoryDTOList;
    }
}
