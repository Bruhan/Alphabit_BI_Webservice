package com.facility.management.usecases.wastage.dto;

import com.facility.management.usecases.wastage.enums.MoveOWCType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MoveOWCOutcomeProductDTO {
    private String product;
    private MoveOWCType moveOWCType;
    private double qty;
    private String uom;
}
