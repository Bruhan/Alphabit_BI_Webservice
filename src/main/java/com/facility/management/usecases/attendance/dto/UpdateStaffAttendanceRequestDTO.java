package com.facility.management.usecases.attendance.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateStaffAttendanceRequestDTO {
    private String empNo;
    private int empId;
    private String oldAttendanceDate;
    private String attendanceDate;
    private String attendanceInTime;
    private String attendanceOutTime;
    private String attendanceType;
}
