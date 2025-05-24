package com.facility.management.usecases.wastage_movement.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class WasteMovementDTO {
    private Integer id;
    private String plant;
    private String date;
    private String vehicleNo;
    private String driverNo;
    private String finalDestination;
    private String remarks;
    private String deliveryChallanNo;
    private String vehicleType;
    private List<WasteMovementDETOutDTO> wasteMovementDETList;
}
