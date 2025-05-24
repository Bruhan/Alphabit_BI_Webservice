package com.facility.management.usecases.wastage_movement.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransportCalendarResponseDTO {
    private String date;
    private int hasTransport;
}
