package com.facility.management.usecases.project.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectDTO {
    private String projectCode;
    private String projectName;
    private String projectDate;
    private String projectStatus;
    private String supervisorCode;
    private String supervisorName;
    private String location;
}
