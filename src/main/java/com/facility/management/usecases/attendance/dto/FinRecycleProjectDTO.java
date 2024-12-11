package com.facility.management.usecases.attendance.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FinRecycleProjectDTO {
    private String plant;
    private int id;
    private String custNo;
    private String projectNo;
    private String projectName;
    private String projectStatus;
}
