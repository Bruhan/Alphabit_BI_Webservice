package com.owner.process.usecases.wastage.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OWCOutcomeDTO {
    private String projectNo;
    private String machineId;
    private String machineName;
    private String productName;
    private String date;
    private double qty;
    private String uom;
    private String empCode;
}
