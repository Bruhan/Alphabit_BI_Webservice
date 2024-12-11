package com.facility.management.usecases.attendance.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;

@Getter
@Setter
public class ProjectWorkerRequestDTO {
    private String plant;
    private BigInteger empId;
    private String empNo;
    private String empName;
    private String currentProjectNo;
}
