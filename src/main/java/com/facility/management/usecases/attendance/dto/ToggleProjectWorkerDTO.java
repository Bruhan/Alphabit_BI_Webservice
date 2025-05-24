package com.facility.management.usecases.attendance.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;

@Getter
@Setter
public class ToggleProjectWorkerDTO {
    private String plant;
    private String currentProjectNo;
    private String date;
    private BigInteger empId;
    private String empNo;
    private int status;
}
