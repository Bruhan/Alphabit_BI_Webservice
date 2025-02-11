package com.facility.management.usecases.wastage.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OWCMachineProductDTO {
    private String item;
    private String productName;
    private double qty;
    private String uom;
}
