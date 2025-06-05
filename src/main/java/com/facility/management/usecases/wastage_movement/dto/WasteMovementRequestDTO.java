package com.facility.management.usecases.wastage_movement.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class  WasteMovementRequestDTO {
    private String empNo;
    private String projectNo;
    private String vehicleNo;
    private String driverNo;
    private String destination;
    private String remarks;
    private String gatepassNo;
    private String vehicleType;
    private List<WasteMovementDETDTO> wasteMovementDETList;
}
