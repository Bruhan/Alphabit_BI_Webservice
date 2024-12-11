package com.facility.management.usecases.wastage.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OWCMachineRequestDTO {
    private String projectNo;
    private String empCode;
    private List<OWCMachineProduct> owcMachineProducts;
}

