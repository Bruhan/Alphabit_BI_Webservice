package com.facility.management.usecases.wastage_movement.dao;

import com.facility.management.persistence.models.WasteMovementDET;
import com.facility.management.usecases.attendance.dto.CalendarRequestDTO;
import com.facility.management.usecases.wastage_movement.dto.*;

import java.text.ParseException;
import java.util.List;

public interface WasteMovementDao {

    Integer saveWasteMovement(String plant, WasteMovementRequestDTO wasteMovementRequestDTO);

    Integer saveWasteMovementDET(String plant, String projectNo, Integer hdrId, WasteMovementDETDTO wasteMovementDETDTO);

    Integer saveWasteMovementInorganicProductDET(String plant, String projectNo, Integer hdrId, Integer detId, WasteMovementInorganicProductDTO wasteMovementInorganicProductDTO);

    Integer saveWasteMovementOWCOutcomeDET(String plant, String projectNo, Integer hdrId, Integer detId, WasteMovementOWCOutcomeDTO wasteMovementOWCOutcomeDTO);

    Integer deleteWasteMovementInorganicProductDET(String plant, String projectNo, Integer hdrId, Integer detId);

    Integer deleteWasteMovementOWCOutcomeDET(String plant, String projectNo, Integer hdrId, Integer detId);

    List<WasteMovementDTO> getWastageMovementSummary(String plant, String projectNo, String date);

    WasteMovementDTO getWastageMovementSummaryById(String plant, String projectNo, Integer id);

    List<WasteMovementDETOutDTO> getWasteMovementDET(String plant, String projectNo, Integer hdrId);

    List<WasteMovementInorganicProductOutDTO> getWasteMovementInorganicProducts(String plant, String projectNo, Integer hdrId, Integer detId);

    List<WasteMovementOWCOutcomeOutDTO> getWasteMovementOWCOutcomeProducts(String plant, String projectNo, Integer hdrId, Integer detId);

    Integer updateWasteMovement(String plant, Integer id, UpdateWasteMovementRequestDTO updateWasteMovementRequestDTO);

    List<TransportCalendarResponseDTO> hasTransport(String plant, String projectNo, CalendarRequestDTO calendarRequestDTO) throws ParseException;
    public Integer updateGatePassSign(String plant, Integer id, String gatePassSignpath);
    public Integer updateDCSign(String plant, Integer id, String customerId,String dcSignPath);
}
