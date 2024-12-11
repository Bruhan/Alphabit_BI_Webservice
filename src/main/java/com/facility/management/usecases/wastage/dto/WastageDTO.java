package com.facility.management.usecases.wastage.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WastageDTO {
    private String projectNo;
    private String wastageType;
    private double wastageQty;
    private String wastageUom;
    private String supervisorCode;
    private String date;
}
