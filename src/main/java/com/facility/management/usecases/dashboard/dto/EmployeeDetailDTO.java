package com.facility.management.usecases.dashboard.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeDetailDTO {
    private String employeeId;
    private String employeeName;

    @JsonIgnore
    private String employeeImagePath;

    private byte[] employeeImage;
}
