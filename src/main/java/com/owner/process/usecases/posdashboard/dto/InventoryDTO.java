package com.owner.process.usecases.posdashboard.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InventoryDTO {
    private String item;
    private String itemDesc;
    private String loc;
    private double qty;
}
