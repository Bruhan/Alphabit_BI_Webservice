package com.owner.process.usecases.wastage_movement.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WasteMovementDTO {
    private String date;
    private String vehicleNo;
    private String driverNo;
    private String finalDestination;
}
