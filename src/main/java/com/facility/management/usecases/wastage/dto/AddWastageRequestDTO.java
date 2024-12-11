package com.facility.management.usecases.wastage.dto;

import com.facility.management.usecases.wastage.enums.WastageType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddWastageRequestDTO {
    private String plant;
    private String projectNo;
    private WastageType wastageType;
    private float wastageQty;
    private String wastageUOM;
    private String supervisorCode;
    private String date;
}
