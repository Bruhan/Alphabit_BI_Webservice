package com.facility.management.usecases.attendance.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CalendarRequestDTO {
    private List<String> dateList;
}
