package com.owner.process.usecases.wastage.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BioGasDTO {
    private String projectNo;
    private String date;
    private double qty;
    private String uom;
    private String empCode;
}
