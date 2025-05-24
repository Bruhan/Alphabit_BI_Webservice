package com.facility.management.usecases.product_request.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PRCalendarResponseDTO {
    private String date;
    private int hasProductRequest;
}
