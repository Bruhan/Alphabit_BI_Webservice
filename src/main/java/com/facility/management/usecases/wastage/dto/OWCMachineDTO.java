package com.facility.management.usecases.wastage.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OWCMachineDTO {
    private String projectNo;
    private String date;
    private String machineId;
    private String machineName;
    private double qty;
    private String uom;
    private String empCode;
}
