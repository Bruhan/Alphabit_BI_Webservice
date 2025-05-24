package com.facility.management.usecases.wastage.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WastageCalendarResponseDTO {
    private String date;
    private int hasWastage;
}
