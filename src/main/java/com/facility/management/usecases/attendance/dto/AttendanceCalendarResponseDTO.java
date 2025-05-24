package com.facility.management.usecases.attendance.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AttendanceCalendarResponseDTO {
    private String date;
    private int hasAttendance;
}
