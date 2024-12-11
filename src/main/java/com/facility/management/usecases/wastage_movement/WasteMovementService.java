package com.facility.management.usecases.wastage_movement;

import com.facility.management.persistence.models.*;
import com.facility.management.usecases.wastage.dao.WastageDao;
import com.facility.management.usecases.wastage_movement.dao.WasteMovementDao;
import com.facility.management.usecases.wastage_movement.dto.*;
import com.facility.management.usecases.wastage_movement.enums.WastageType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class WasteMovementService {

    @Autowired
    WastageDao wastageDao;

    @Autowired
    WasteMovementDao wasteMovementDao;

    public HashMap<String, Integer> saveWasteMovement(String plant, WasteMovementRequestDTO wasteMovementRequestDTO) {
        HashMap<String, Integer> result = new HashMap<>();
        try {
            for(WasteMovementDETDTO wasteMovementDETDTO: wasteMovementRequestDTO.getWasteMovementDETList()) {
                if(wasteMovementDETDTO.getWastageType() == WastageType.BIOGAS) {
                    BioGasHDR bioGasHDR = wastageDao.getBioGasHDR(plant, wasteMovementRequestDTO.getProjectNo());
                    bioGasHDR.setProcessedQty(bioGasHDR.getProcessedQty() + wasteMovementDETDTO.getQty());
                    bioGasHDR.setPendingQty(bioGasHDR.getPendingQty() - wasteMovementDETDTO.getQty());
                    Integer bioGasHdrUpdated = wastageDao.updateBioGasHdr(plant, bioGasHDR);
                    result.put("bioGasHdrUpdated - ", bioGasHdrUpdated);

                } else if(wasteMovementDETDTO.getWastageType() == WastageType.OWCOUTCOME) {
                    OWCOutcomeHDR owcOutcomeHDR = wastageDao.getOWCOutcomeHDR(plant, wasteMovementRequestDTO.getProjectNo());
                    owcOutcomeHDR.setProcessedQty(owcOutcomeHDR.getProcessedQty() + wasteMovementDETDTO.getQty());
                    owcOutcomeHDR.setPendingQty(owcOutcomeHDR.getPendingQty() - wasteMovementDETDTO.getQty());
                    Integer owcOutcomeUpdated = wastageDao.updateOWCOutcomeHdr(plant, owcOutcomeHDR);
                    result.put("owcOutcomeUpdated", owcOutcomeUpdated);



                } else if(wasteMovementDETDTO.getWastageType() == WastageType.INORGANIC_WASTE) {
                    InorganicWasteHDR inorganicWasteHDR = wastageDao.getInorganicWasteHDR(plant, wasteMovementRequestDTO.getProjectNo());
                    inorganicWasteHDR.setProcessedQty(inorganicWasteHDR.getProcessedQty() + wasteMovementDETDTO.getQty());
                    inorganicWasteHDR.setPendingQty(inorganicWasteHDR.getPendingQty() - wasteMovementDETDTO.getQty());
                    Integer inorganicWasteHdrUpdated = wastageDao.updateInorganicWasteHdr(plant, inorganicWasteHDR);
                    result.put("inorganicWasteHdrUpdated", inorganicWasteHdrUpdated);

                    for(WasteMovementInorganicProductDTO wasteMovementInorganicProductDTO: wasteMovementDETDTO.getWasteMovementInorganicProductList()) {
                        InorganicWasteProductDET inorganicWasteProductDET = wastageDao.getInorganicWasteProductDET(plant, wasteMovementRequestDTO.getProjectNo(), wasteMovementInorganicProductDTO.getItem());
                        inorganicWasteProductDET.setProcessedQty(inorganicWasteProductDET.getProcessedQty() + wasteMovementInorganicProductDTO.getQty());
                        inorganicWasteProductDET.setPendingQty(inorganicWasteProductDET.getPendingQty() - wasteMovementInorganicProductDTO.getQty());
                        Integer inorganicWasteProductDetUpdated = wastageDao.updateInorganicWasteProductDet(plant, inorganicWasteProductDET);
                        result.put("inorganicWasteProductDetUpdated - " + wasteMovementInorganicProductDTO.getItem(), inorganicWasteProductDetUpdated);
                    }

                } else if(wasteMovementDETDTO.getWastageType() == WastageType.REJECTED_WASTE) {
                    RejectedWasteHDR rejectedWasteHDR = wastageDao.getRejectedWasteHDR(plant, wasteMovementRequestDTO.getProjectNo());
                    rejectedWasteHDR.setProcessedQty(rejectedWasteHDR.getProcessedQty() + wasteMovementDETDTO.getQty());
                    rejectedWasteHDR.setPendingQty(rejectedWasteHDR.getPendingQty() - wasteMovementDETDTO.getQty());
                    Integer rejectedWasteHdrUpdated = wastageDao.updateRejectedWasteHdr(plant, rejectedWasteHDR);
                    result.put("rejectedWasteHdrUpdated", rejectedWasteHdrUpdated);
                }
            }

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
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        return wasteMovementDTOList;
    }
}
