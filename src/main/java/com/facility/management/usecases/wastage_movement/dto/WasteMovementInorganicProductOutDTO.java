package com.facility.management.usecases.wastage_movement.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WasteMovementInorganicProductOutDTO {
    private Integer id;
    private String item;
    private String productName;
    private double qty;
    private String uom;
}
