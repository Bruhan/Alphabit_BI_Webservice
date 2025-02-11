package com.facility.management.usecases.wastage.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OWCMachineDTO {
    private int id;
    private String projectNo;
    private String date;
    private String machineId;
    private String machineName;
    private double qty;
    private String uom;
    private String empCode;
    private List<OWCMachineProductDTO> owcMachineProductDTOList;
}
