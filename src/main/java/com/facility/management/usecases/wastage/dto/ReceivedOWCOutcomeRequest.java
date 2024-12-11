package com.facility.management.usecases.wastage.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ReceivedOWCOutcomeRequest {
    private String projectNo;
    private String empCode;
    private List<ReceivedOWCOutcomeProduct> receivedOWCOutcomeProducts;
}
