package com.owner.process.usecases.wastage_movement.dao;

import com.owner.process.usecases.wastage_movement.dto.WasteMovementDTO;
import com.owner.process.usecases.wastage_movement.dto.WasteMovementRequestDTO;

import java.util.List;

public interface WasteMovementDao {

    public Integer saveWasteMovement(String plant, WasteMovementRequestDTO wasteMovementRequestDTO);

    List<WasteMovementDTO> getWastageMovementSummary(String plant, String projectNo);
}
