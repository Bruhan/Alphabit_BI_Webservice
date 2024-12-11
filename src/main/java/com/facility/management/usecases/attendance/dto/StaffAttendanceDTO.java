package com.facility.management.usecases.attendance.dto;

import com.facility.management.usecases.attendance.enums.ShiftStatus;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;
import java.util.Date;

@Getter
@Setter
public class StaffAttendanceDTO {
    private String plant;
    private BigInteger id;
    private BigInteger empId;
    private String employeeName;
    private String employeeNo;
    private String attendanceDate;
    private String attendanceTime;
    private String shiftStatus;
    private String locationLat;
    private String locationLong;
    private double permission;
    private String narrations;
    private BigInteger staffMonthId;
}
