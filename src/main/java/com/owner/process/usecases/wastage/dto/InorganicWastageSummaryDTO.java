package com.owner.process.usecases.wastage.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InorganicWastageSummaryDTO {
    private String projectNo;
    private String date;
    private String productName;
    private double qty;
    private String uom;
}
