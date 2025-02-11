package com.facility.management.usecases.wastage.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MovedOWCOutcomeDTO {
    private String projectNo;
    private String type;
    private String item;
    private String productName;
    private String date;
    private double qty;
    private String uom;
}
