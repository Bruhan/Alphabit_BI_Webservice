package com.facility.management.usecases.wastage.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReceivedOWCOutcomeProduct {
    private String machineId;
    private String product;
    private double qty;
    private String uom;
}
