package com.facility.management.usecases.wastage.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DailyWastageDetailsHDRDTO {
    private String plant;
    private int id;
    private String totalWastageType;
    private double totalWastageQty;
    private String totalWastageUOM;
    private String projectNo;
    private double processedQty;
    private double pendingQty;
}
