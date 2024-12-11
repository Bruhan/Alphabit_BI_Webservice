package com.facility.management.usecases.wastage_movement.dao;

import com.facility.management.usecases.wastage_movement.dto.WasteMovementDTO;
import com.facility.management.usecases.wastage_movement.dto.WasteMovementRequestDTO;

import java.util.List;

public interface WasteMovementDao {

    public Integer saveWasteMovement(String plant, WasteMovementRequestDTO wasteMovementRequestDTO);

    List<WasteMovementDTO> getWastageMovementSummary(String plant, String projectNo);
}
