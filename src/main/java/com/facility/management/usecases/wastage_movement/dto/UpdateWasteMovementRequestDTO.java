package com.facility.management.usecases.wastage_movement.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UpdateWasteMovementRequestDTO {
    private Integer id;
    private String empNo;
    private String date;
    private String projectNo;
    private String vehicleNo;
    private String driverNo;
    private String destination;
    private String remarks;
    private String gatePassNo;
    private Short isGpSigned;
    private String inspectingPersonSign;
    private String vehicleType;
    private List<WasteMovementDETDTO> wasteMovementDETList;
}
