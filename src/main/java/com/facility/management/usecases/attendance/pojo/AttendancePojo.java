package com.facility.management.usecases.attendance.pojo;

public interface AttendancePojo {
    int getAttendanceId();
    String getPlant();
    String getEmpNo();
    String getEmpName();
    String getDate();
    String getInTime();
    String getOutTime();
    String getInTimeLocation();
    String getOutTimeLocation();
    String getInTimeFacePath();
    String getOutTimeFacePath();
}
