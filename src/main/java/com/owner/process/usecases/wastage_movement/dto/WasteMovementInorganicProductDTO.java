package com.owner.process.usecases.wastage_movement.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WasteMovementInorganicProductDTO {
    private String item;
    private double qty;
    private String uom;
}
