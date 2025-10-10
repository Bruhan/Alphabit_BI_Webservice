package com.owner.process.usecases.toolbox.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ToolboxDao {
    private String Plant;
    private int Id;
    private String Hdrdate;
    private int ToolboxMeetingId;
    private String ToolboxMeetingName;
    private String EmployeeCode;
    private String SupervisorCode;
    private String Status;
}
