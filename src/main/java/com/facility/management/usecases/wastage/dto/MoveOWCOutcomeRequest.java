package com.facility.management.usecases.wastage.dto;

import com.facility.management.usecases.wastage.enums.MoveOWCType;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MoveOWCOutcomeRequest {
    String projectNo;
    String empCode;
    List<MoveOWCOutcomeProductDTO> moveOWCOutcomeProducts;
}
