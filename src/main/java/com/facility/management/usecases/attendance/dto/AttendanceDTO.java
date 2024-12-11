package com.facility.management.usecases.attendance.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AttendanceDTO {
    private int attendanceId;
    private String plant;
    private String empNo;
    private String empName;
    private String date;
    private String inTime;
    private String outTime;
    private String inTimeLocation;
    private String outTimeLocation;

    @JsonIgnore
    private String inTimeFacePath;
    @JsonIgnore
    private String outTimeFacePath;

    private byte[] inTimeFace;
    private byte[] outTimeFace;
}
