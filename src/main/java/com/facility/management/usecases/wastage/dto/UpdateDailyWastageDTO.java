package com.facility.management.usecases.wastage.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateDailyWastageDTO {
    private int id;
    private String date;
    private String wastageType;
    private double wastageQty;
    private String uom;
    private String executiveId;
}
