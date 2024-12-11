package com.facility.management.usecases.wastage.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OWCMachineProduct {
    private String machineId;
    private double qty;
    private String uom;
    private List<OWCProductDTO> owcMachineProductDTOList;
}
