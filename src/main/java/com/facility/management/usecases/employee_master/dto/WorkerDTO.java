package com.facility.management.usecases.employee_master.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WorkerDTO {
    private int id;
    private String empNo;
    private String empName;
    private String plant;
}
