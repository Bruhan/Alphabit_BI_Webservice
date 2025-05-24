package com.facility.management.usecases.attendance.dto;

import com.facility.management.usecases.attendance.enums.AttendanceType;
import com.facility.management.usecases.attendance.enums.ShiftStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StaffAttendanceRequestDTO {
    private String empNo;
    private String locationLat;
    private String locationLong;
    private String attendanceDate;
    private String attendanceTime;
    private String attendanceType;
    private int isExecutive;
}
