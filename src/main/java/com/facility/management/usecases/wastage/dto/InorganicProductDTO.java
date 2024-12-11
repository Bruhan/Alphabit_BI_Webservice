package com.facility.management.usecases.wastage.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InorganicProductDTO {
    private String item;
    private double qty;
    private String uom;
}
