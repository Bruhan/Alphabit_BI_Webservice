package com.facility.management.usecases.wastage_movement;

import com.facility.management.helpers.common.calc.DateTimeCalc;
import com.facility.management.persistence.models.*;
import com.facility.management.usecases.activity_log.ActivityLogModel;
import com.facility.management.usecases.activity_log.ActivityLogService;
import com.facility.management.usecases.wastage.dao.WastageDao;
import com.facility.management.usecases.wastage_movement.dao.WasteMovementDao;
import com.facility.management.usecases.wastage_movement.dto.*;
import com.facility.management.usecases.wastage_movement.enums.WastageType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Service
public class WasteMovementService {

    @Autowired
    WastageDao wastageDao;

    @Autowired
    WasteMovementDao wasteMovementDao;

    @Autowired
    ActivityLogService activityLogService;

    public HashMap<String, Integer> saveWasteMovement(String plant, WasteMovementRequestDTO wasteMovementRequestDTO) {
        HashMap<String, Integer> result = new HashMap<>();
        DateTimeCalc dateTimeCalc = new DateTimeCalc();
        ActivityLogModel activityLogModel = new ActivityLogModel();
        try {
            for(WasteMovementDETDTO wasteMovementDETDTO: wasteMovementRequestDTO.getWasteMovementDETList()) {
                if(wasteMovementDETDTO.getWastageType() == WastageType.BIOGAS) {
                    BioGasHDR bioGasHDR = wastageDao.getBioGasHDR(plant, wasteMovementRequestDTO.getProjectNo());
                    bioGasHDR.setProcessedQty(bioGasHDR.getProcessedQty() + wasteMovementDETDTO.getQty());
                    bioGasHDR.setPendingQty(bioGasHDR.getPendingQty() - wasteMovementDETDTO.getQty());
                    activityLogService.setActivityLogDetails(activityLogModel.setActivityLogModel(
                            plant, "UPDATE_BIOGAS_HDR", "", "", "", 0.0,
                            wasteMovementRequestDTO.getEmpNo(), dateTimeCalc.getTodayDMYDate(), dateTimeCalc.getTodayDMYDate(), wasteMovementRequestDTO.getEmpNo(),
                            "CREATED", ""));
                    Integer bioGasHdrUpdated = wastageDao.updateBioGasHdr(plant, bioGasHDR);
                    result.put("bioGasHdrUpdated - ", bioGasHdrUpdated);

                } else if(wasteMovementDETDTO.getWastageType() == WastageType.OWCOUTCOME || wasteMovementDETDTO.getWastageType() == WastageType.COMPOSE_OR_CATTLE_FEEDS) {
                    OWCOutcomeHDR owcOutcomeHDR = wastageDao.getOWCOutcomeHDR(plant, wasteMovementRequestDTO.getProjectNo());
                    owcOutcomeHDR.setProcessedQty(owcOutcomeHDR.getProcessedQty() + wasteMovementDETDTO.getQty());
                    owcOutcomeHDR.setPendingQty(owcOutcomeHDR.getPendingQty() - wasteMovementDETDTO.getQty());
                    activityLogService.setActivityLogDetails(activityLogModel.setActivityLogModel(
                            plant, "UPDATE_OWC_OUTCOME_HDR", "", "", "", 0.0,
                            wasteMovementRequestDTO.getEmpNo(), dateTimeCalc.getTodayDMYDate(), dateTimeCalc.getTodayDMYDate(), wasteMovementRequestDTO.getEmpNo(),
                            "CREATED", ""));
                    Integer owcOutcomeUpdated = wastageDao.updateOWCOutcomeHdr(plant, owcOutcomeHDR);
                    result.put("owcOutcomeUpdated", owcOutcomeUpdated);

                } else if(wasteMovementDETDTO.getWastageType() == WastageType.INORGANIC_WASTE) {
                    InorganicWasteHDR inorganicWasteHDR = wastageDao.getInorganicWasteHDR(plant, wasteMovementRequestDTO.getProjectNo());
                    inorganicWasteHDR.setProcessedQty(inorganicWasteHDR.getProcessedQty() + wasteMovementDETDTO.getQty());
                    inorganicWasteHDR.setPendingQty(inorganicWasteHDR.getPendingQty() - wasteMovementDETDTO.getQty());
                    activityLogService.setActivityLogDetails(activityLogModel.setActivityLogModel(
                            plant, "UPDATE_INORGANIC_WASTE_HDR", "", "", "", 0.0,
                            wasteMovementRequestDTO.getEmpNo(), dateTimeCalc.getTodayDMYDate(), dateTimeCalc.getTodayDMYDate(), wasteMovementRequestDTO.getEmpNo(),
                            "CREATED", ""));
                    Integer inorganicWasteHdrUpdated = wastageDao.updateInorganicWasteHdr(plant, inorganicWasteHDR);
                    result.put("inorganicWasteHdrUpdated", inorganicWasteHdrUpdated);

                    for(WasteMovementInorganicProductDTO wasteMovementInorganicProductDTO: wasteMovementDETDTO.getWasteMovementInorganicProductList()) {
                        InorganicWasteProductDET inorganicWasteProductDET = wastageDao.getInorganicWasteProductDET(plant, wasteMovementRequestDTO.getProjectNo(), wasteMovementInorganicProductDTO.getItem());
                        inorganicWasteProductDET.setProcessedQty(inorganicWasteProductDET.getProcessedQty() + wasteMovementInorganicProductDTO.getQty());
                        inorganicWasteProductDET.setPendingQty(inorganicWasteProductDET.getPendingQty() - wasteMovementInorganicProductDTO.getQty());
                        activityLogService.setActivityLogDetails(activityLogModel.setActivityLogModel(
                                plant, "UPDATE_INORGANIC_WASTE_PRODUCT_DET", "", "", "", 0.0,
                                wasteMovementRequestDTO.getEmpNo(), dateTimeCalc.getTodayDMYDate(), dateTimeCalc.getTodayDMYDate(), wasteMovementRequestDTO.getEmpNo(),
                                "CREATED", ""));
                        Integer inorganicWasteProductDetUpdated = wastageDao.updateInorganicWasteProductDet(plant, inorganicWasteProductDET);
                        result.put("inorganicWasteProductDetUpdated - " + wasteMovementInorganicProductDTO.getItem(), inorganicWasteProductDetUpdated);
                    }

                } else if(wasteMovementDETDTO.getWastageType() == WastageType.REJECTED_WASTE || wasteMovementDETDTO.getWastageType() == WastageType.DEBRIS_WASTE || wasteMovementDETDTO.getWastageType() == WastageType.GARDEN_WASTE) {
                    RejectedWasteHDR rejectedWasteHDR = wastageDao.getRejectedWasteHDR(plant, wasteMovementRequestDTO.getProjectNo());
                    rejectedWasteHDR.setProcessedQty(rejectedWasteHDR.getProcessedQty() + wasteMovementDETDTO.getQty());
                    rejectedWasteHDR.setPendingQty(rejectedWasteHDR.getPendingQty() - wasteMovementDETDTO.getQty());
                    activityLogService.setActivityLogDetails(activityLogModel.setActivityLogModel(
                            plant, "UPDATE_REJECTED_WASTE_HDR", "", "", "", 0.0,
                            wasteMovementRequestDTO.getEmpNo(), dateTimeCalc.getTodayDMYDate(), dateTimeCalc.getTodayDMYDate(), wasteMovementRequestDTO.getEmpNo(),
                            "CREATED", ""));
                    Integer rejectedWasteHdrUpdated = wastageDao.updateRejectedWasteHdr(plant, rejectedWasteHDR);
                    result.put("rejectedWasteHdrUpdated", rejectedWasteHdrUpdated);

                    DailyWastageDetailsHDR oldDailyWastageDetailsHDR = wastageDao.getDailyWastageDetailsHDR(plant, wasteMovementDETDTO.getWastageType().toString(), wasteMovementRequestDTO.getProjectNo()); //organic waste is hardcoded
                    oldDailyWastageDetailsHDR.setPendingQty(oldDailyWastageDetailsHDR.getPendingQty() - wasteMovementDETDTO.getQty());
                    oldDailyWastageDetailsHDR.setProcessedQty(oldDailyWastageDetailsHDR.getProcessedQty() + wasteMovementDETDTO.getQty());

                    activityLogService.setActivityLogDetails(activityLogModel.setActivityLogModel(
                            plant, "UPDATE_DAILY_WASTAGE_HDR", "", "", "", 0.0,
                            wasteMovementRequestDTO.getEmpNo(), dateTimeCalc.getTodayDMYDate(), dateTimeCalc.getTodayDMYDate(), wasteMovementRequestDTO.getEmpNo(),
                            "CREATED", ""));
                    Integer dailyWastageHdrUpdated = wastageDao.updateDailyWastageHdr(plant, oldDailyWastageDetailsHDR);
                    result.put("dailyWastageHdrUpdated", dailyWastageHdrUpdated);
                }

                if(wasteMovementDETDTO.getWastageType() != WastageType.MIXED_WASTE && wasteMovementDETDTO.getWastageType() != WastageType.INORGANIC_WASTE && wasteMovementDETDTO.getWastageType() != WastageType.OWCOUTCOME ) {
                    ProjectInventory existingProjectInventory = wastageDao.getProjectInventory(plant, wasteMovementRequestDTO.getProjectNo(), wasteMovementDETDTO.getWastageType() == WastageType.COMPOSE_OR_CATTLE_FEEDS ? WastageType.OWCOUTCOME.name() : wasteMovementDETDTO.getWastageType().name());

                    existingProjectInventory.setProcessedQty(existingProjectInventory.getProcessedQty() + wasteMovementDETDTO.getQty());
                    existingProjectInventory.setPendingQty(existingProjectInventory.getPendingQty() - wasteMovementDETDTO.getQty());

                    activityLogService.setActivityLogDetails(activityLogModel.setActivityLogModel(
                            plant, "UPDATE_PROJECT_INVENTORY", "", "", "", 0.0,
                            wasteMovementRequestDTO.getEmpNo(), dateTimeCalc.getTodayDMYDate(), dateTimeCalc.getTodayDMYDate(), wasteMovementRequestDTO.getEmpNo(),
                            "CREATED", ""));
                    Integer projectInventoryUpdated = wastageDao.updateProjectInventory(plant, existingProjectInventory, wasteMovementDETDTO.getWastageType().name());
                }

                if(wasteMovementDETDTO.getWastageType() == WastageType.INORGANIC_WASTE) {
                    for(WasteMovementInorganicProductDTO wasteMovementInorganicProductDTO: wasteMovementDETDTO.getWasteMovementInorganicProductList()) {
                        ProjectInventory existingProjectInventory = wastageDao.getProjectInventory(plant, wasteMovementRequestDTO.getProjectNo(), wasteMovementInorganicProductDTO.getItem());

                        existingProjectInventory.setProcessedQty(existingProjectInventory.getProcessedQty() + wasteMovementInorganicProductDTO.getQty());
                        existingProjectInventory.setPendingQty(existingProjectInventory.getPendingQty() - wasteMovementInorganicProductDTO.getQty());

                        activityLogService.setActivityLogDetails(activityLogModel.setActivityLogModel(
                                plant, "UPDATE_PROJECT_INVENTORY", "", "", "", 0.0,
                                wasteMovementRequestDTO.getEmpNo(), dateTimeCalc.getTodayDMYDate(), dateTimeCalc.getTodayDMYDate(), wasteMovementRequestDTO.getEmpNo(),
                                "CREATED", ""));
                        Integer projectInventoryUpdated = wastageDao.updateProjectInventory(plant, existingProjectInventory, wasteMovementInorganicProductDTO.getItem());
                    }
                }

                if(wasteMovementDETDTO.getWastageType() == WastageType.OWCOUTCOME) {
                    for(WasteMovementOWCOutcomeDTO wasteMovementOWCOutcomeDTO: wasteMovementDETDTO.getWasteMovementOWCOutcomeList()) {
                        ProjectInventory existingProjectInventory = wastageDao.getProjectInventory(plant, wasteMovementRequestDTO.getProjectNo(), wasteMovementOWCOutcomeDTO.getItem());

                        existingProjectInventory.setProcessedQty(existingProjectInventory.getProcessedQty() + wasteMovementOWCOutcomeDTO.getQty());
                        existingProjectInventory.setPendingQty(existingProjectInventory.getPendingQty() - wasteMovementOWCOutcomeDTO.getQty());

                        activityLogService.setActivityLogDetails(activityLogModel.setActivityLogModel(
                                plant, "UPDATE_PROJECT_INVENTORY", "", "", "", 0.0,
                                wasteMovementRequestDTO.getEmpNo(), dateTimeCalc.getTodayDMYDate(), dateTimeCalc.getTodayDMYDate(), wasteMovementRequestDTO.getEmpNo(),
                                "CREATED", ""));
                        Integer projectInventoryUpdated = wastageDao.updateProjectInventory(plant, existingProjectInventory, wasteMovementOWCOutcomeDTO.getItem());
                    }
                }

            }

            activityLogService.setActivityLogDetails(activityLogModel.setActivityLogModel(
                    plant, "SAVE_WASTE_MOVEMENT", "", "", "", 0.0,
                    wasteMovementRequestDTO.getEmpNo(), dateTimeCalc.getTodayDMYDate(), dateTimeCalc.getTodayDMYDate(), wasteMovementRequestDTO.getEmpNo(),
                    "CREATED", ""));
            Integer wasteMovementInserted = wasteMovementDao.saveWasteMovement(plant, wasteMovementRequestDTO);
            result.put("wasteMovementInserted", wasteMovementInserted);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        return result;
    }

    public List<WasteMovementDTO> getWastageMovementSummary(String plant, String projectNo) {
        List<WasteMovementDTO> wasteMovementDTOList = null;
        try {
            wasteMovementDTOList = wasteMovementDao.getWastageMovementSummary(plant, projectNo);

            for(WasteMovementDTO wasteMovementDTO: wasteMovementDTOList) {
                List<WasteMovementDETOutDTO> wasteMovementDETList = wasteMovementDao.getWasteMovementDET(plant, projectNo, wasteMovementDTO.getId());
                for(WasteMovementDETOutDTO wasteMovementDETDTO: wasteMovementDETList) {
                    if(wasteMovementDETDTO.getWastageType() ==  WastageType.INORGANIC_WASTE) {
                        List<WasteMovementInorganicProductOutDTO> wasteMovementInorganicProductDTOList = wasteMovementDao.getWasteMovementInorganicProducts(plant, projectNo, wasteMovementDTO.getId(), wasteMovementDETDTO.getId());
                        wasteMovementDETDTO.setWasteMovementInorganicProductList(wasteMovementInorganicProductDTOList);
                    }

                    if(wasteMovementDETDTO.getWastageType() == WastageType.OWCOUTCOME) {
                        List<WasteMovementOWCOutcomeOutDTO> wasteMovementOWCOutcomeOutDTOList = wasteMovementDao.getWasteMovementOWCOutcomeProducts(plant, projectNo, wasteMovementDTO.getId(), wasteMovementDETDTO.getId());
                        wasteMovementDETDTO.setWasteMovementOWCOutcomeList(wasteMovementOWCOutcomeOutDTOList);
                    }
                }
                wasteMovementDTO.setWasteMovementDETList(wasteMovementDETList);
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        return wasteMovementDTOList;
    }

    public Integer updateWasteMovement(String plant, Integer id, UpdateWasteMovementRequestDTO updateWasteMovementRequestDTO) {
        Integer result = 0;
        DateTimeCalc dateTimeCalc = new DateTimeCalc();
        ActivityLogModel activityLogModel = new ActivityLogModel();
        try {
            WasteMovementDTO wasteMovementDTO = wasteMovementDao.getWastageMovementSummaryById(plant, updateWasteMovementRequestDTO.getProjectNo(), id);

            List<WasteMovementDETOutDTO> wasteMovementDETList = wasteMovementDao.getWasteMovementDET(plant, updateWasteMovementRequestDTO.getProjectNo(), wasteMovementDTO.getId());
            for(WasteMovementDETOutDTO wasteMovementDETDTO: wasteMovementDETList) {
                if(wasteMovementDETDTO.getWastageType() ==  WastageType.INORGANIC_WASTE) {
                    List<WasteMovementInorganicProductOutDTO> wasteMovementInorganicProductDTOList = wasteMovementDao.getWasteMovementInorganicProducts(plant, updateWasteMovementRequestDTO.getProjectNo(), wasteMovementDTO.getId(), wasteMovementDETDTO.getId());
                    wasteMovementDETDTO.setWasteMovementInorganicProductList(wasteMovementInorganicProductDTOList);
                }

                if(wasteMovementDETDTO.getWastageType() == WastageType.OWCOUTCOME) {
                    List<WasteMovementOWCOutcomeOutDTO> wasteMovementOWCOutcomeOutDTOList = wasteMovementDao.getWasteMovementOWCOutcomeProducts(plant, updateWasteMovementRequestDTO.getProjectNo(), wasteMovementDTO.getId(), wasteMovementDETDTO.getId());
                    wasteMovementDETDTO.setWasteMovementOWCOutcomeList(wasteMovementOWCOutcomeOutDTOList);
                }
            }
            wasteMovementDTO.setWasteMovementDETList(wasteMovementDETList);

            if(Objects.equals(updateWasteMovementRequestDTO.getId(), wasteMovementDTO.getId())) {
                for(WasteMovementDETOutDTO wasteMovementDETOutDTO: wasteMovementDETList) {
                    for(WasteMovementDETDTO updateWasteMovementDETDTO : updateWasteMovementRequestDTO.getWasteMovementDETList()) { //Will identify if any new DET is appeared
                        if(!Objects.equals(updateWasteMovementDETDTO.getId(), wasteMovementDETOutDTO.getId())) {
                            WasteMovementDETDTO wasteMovementDETDTO1 = WasteMovementDETDTO.builder()
                                    .customerId(updateWasteMovementDETDTO.getCustomerId())
                                    .customerName(updateWasteMovementDETDTO.getCustomerName())
                                    .destination(updateWasteMovementDETDTO.getDestination())
                                    .destinationType(updateWasteMovementDETDTO.getDestinationType())
                                    .wastageType(updateWasteMovementDETDTO.getWastageType())
                                    .qty(updateWasteMovementDETDTO.getQty())
                                    .uom(updateWasteMovementDETDTO.getUom())
                                    .build();

                            Integer detId = wasteMovementDao.saveWasteMovementDET(plant, updateWasteMovementRequestDTO.getProjectNo(), updateWasteMovementDETDTO.getId(), wasteMovementDETDTO1);

                            if(updateWasteMovementDETDTO.getWastageType() == WastageType.INORGANIC_WASTE) {
                                for(WasteMovementInorganicProductDTO updateWasteMovementInorganicProductDTO: updateWasteMovementDETDTO.getWasteMovementInorganicProductList()) {
                                    WasteMovementInorganicProductDTO wasteMovementInorganicProductDTO1 = WasteMovementInorganicProductDTO.builder()
                                            .item(updateWasteMovementInorganicProductDTO.getItem())
                                            .qty(updateWasteMovementInorganicProductDTO.getQty())
                                            .uom(updateWasteMovementInorganicProductDTO.getUom())
                                            .build();

                                    wasteMovementDao.saveWasteMovementInorganicProductDET(plant, updateWasteMovementRequestDTO.getProjectNo(), updateWasteMovementDETDTO.getId(), detId, wasteMovementInorganicProductDTO1);
                                }
                            } else if(updateWasteMovementDETDTO.getWastageType() == WastageType.OWCOUTCOME) {
                                for(WasteMovementOWCOutcomeDTO updateWasteMovementOWCOutcomeDTO: updateWasteMovementDETDTO.getWasteMovementOWCOutcomeList()) {
                                    WasteMovementOWCOutcomeDTO wasteMovementOWCOutcomeDTO1 = WasteMovementOWCOutcomeDTO.builder()
                                            .item(updateWasteMovementOWCOutcomeDTO.getItem())
                                            .qty(updateWasteMovementOWCOutcomeDTO.getQty())
                                            .uom(updateWasteMovementOWCOutcomeDTO.getUom())
                                            .build();

                                    wasteMovementDao.saveWasteMovementOWCOutcomeDET(plant, updateWasteMovementRequestDTO.getProjectNo(), updateWasteMovementDETDTO.getId(), detId, wasteMovementOWCOutcomeDTO1);
                                }
                            }

                        }
                    }

                    for(WasteMovementDETDTO updateWasteMovementDETDTO : updateWasteMovementRequestDTO.getWasteMovementDETList()) {
                        if(updateWasteMovementDETDTO.getWastageType() == WastageType.INORGANIC_WASTE) {
                            for(WasteMovementInorganicProductDTO updateWasteMovementInorganicProductDTO: updateWasteMovementDETDTO.getWasteMovementInorganicProductList()) {
                                if(updateWasteMovementInorganicProductDTO.getId() == 0) {
                                    wasteMovementDao.saveWasteMovementInorganicProductDET(plant, updateWasteMovementRequestDTO.getProjectNo(), id, updateWasteMovementDETDTO.getId(), updateWasteMovementInorganicProductDTO);
                                }
                            }

                        } else if(updateWasteMovementDETDTO.getWastageType() == WastageType.OWCOUTCOME) {
                            for(WasteMovementOWCOutcomeDTO updateWasteMovementOWCOutcomeDTO: updateWasteMovementDETDTO.getWasteMovementOWCOutcomeList()) {
                                if(updateWasteMovementOWCOutcomeDTO.getId() == 0) {
                                    wasteMovementDao.saveWasteMovementOWCOutcomeDET(plant, updateWasteMovementRequestDTO.getProjectNo(), id, updateWasteMovementDETDTO.getId(), updateWasteMovementOWCOutcomeDTO);
                                }
                            }

                        }
                    }
                }
            }

            result = wasteMovementDao.updateWasteMovement(plant, id, updateWasteMovementRequestDTO);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return result;
    }
}
