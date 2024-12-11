package com.facility.management.usecases.wastage.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrganicWastageRequestDTO {
    private String projectNo;
    private String empCode;
    private double organicWastageQty;
    private String organicWastageUOM;
    private double processedOrganicWastageQty;
    private String processedOrganicWastageUOM;
    private double rejectedWastageQty;
    private String rejectedWastageUOM;
}
