package com.facility.management.usecases.wastage_movement.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class WasteMovementInorganicProductDTO {
    private Integer id; //not used in saveWasteMovement
    private String item;
    private double qty;
    private String uom;
}
