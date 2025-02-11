package com.facility.management.usecases.attendance.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;

@Getter
@Setter
public class ProjectWorkerDTO {
    private String plant;
    private int id;
    private String projectNo;
    private BigInteger empId;
    private String empCode;
    private String empName;
//    private String projectInDate;
//    private String projectOutDate;
    private Byte status;
}
