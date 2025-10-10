package com.owner.process.usecases.wastage.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrganicWastageSummaryDTO {
    private String projectNo;
    private String date;
    private double qty;
    private String uom;
}
