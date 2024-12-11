package com.facility.management.usecases.attendance.dto;

import com.facility.management.usecases.attendance.enums.ShiftStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigInteger;

@Getter
@Setter
@ToString
public class StaffAttendanceDetailDTO {
    private BigInteger empId;
    private String employeeName;
    private String employeeNo;
    private String attendanceDate;
    private String eOutAttendanceTime;
    private String minAttendanceTime;
    private String shiftStatus;
    private String eOutLocationLat;
    private String eOutLocationLong;
    private String minLocationLat;
    private String minLocationLong;
}
