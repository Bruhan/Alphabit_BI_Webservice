package com.facility.management.usecases.toolbox.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ToolboxSummary {
    private String plant;
    private int Id;
    private String date;
    private int ToolboxMeetingId;
    private String ToolboxMeetingCode;
    private String EmployeeCode;
    private String SupervisorCode;
    private String Status;
}

