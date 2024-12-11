package com.facility.management.usecases.wastage.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BioGasRequestDTO {
    private String projectNo;
    private double qty;
    private String uom;
    private String empCode;
}
