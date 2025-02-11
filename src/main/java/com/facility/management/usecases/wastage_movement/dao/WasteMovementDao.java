package com.facility.management.usecases.wastage_movement.dao;

import com.facility.management.persistence.models.WasteMovementDET;
import com.facility.management.usecases.wastage_movement.dto.*;

import java.util.List;

public interface WasteMovementDao {

    Integer saveWasteMovement(String plant, WasteMovementRequestDTO wasteMovementRequestDTO);

    Integer saveWasteMovementDET(String plant, String projectNo, Integer hdrId, WasteMovementDETDTO wasteMovementDETDTO);

    Integer saveWasteMovementInorganicProductDET(String plant, String projectNo, Integer hdrId, Integer detId, WasteMovementInorganicProductDTO wasteMovementInorganicProductDTO);

    Integer saveWasteMovementOWCOutcomeDET(String plant, String projectNo, Integer hdrId, Integer detId, WasteMovementOWCOutcomeDTO wasteMovementOWCOutcomeDTO);

    List<WasteMovementDTO> getWastageMovementSummary(String plant, String projectNo);

    WasteMovementDTO getWastageMovementSummaryById(String plant, String projectNo, Integer id);

    List<WasteMovementDETOutDTO> getWasteMovementDET(String plant, String projectNo, Integer hdrId);

    List<WasteMovementInorganicProductOutDTO> getWasteMovementInorganicProducts(String plant, String projectNo, Integer hdrId, Integer detId);

    List<WasteMovementOWCOutcomeOutDTO> getWasteMovementOWCOutcomeProducts(String plant, String projectNo, Integer hdrId, Integer detId);

    Integer updateWasteMovement(String plant, Integer id, UpdateWasteMovementRequestDTO updateWasteMovementRequestDTO);
}
