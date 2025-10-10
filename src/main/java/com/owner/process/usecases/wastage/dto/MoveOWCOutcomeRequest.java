package com.owner.process.usecases.wastage.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MoveOWCOutcomeRequest {
    String projectNo;
    List<MoveOWCOutcomeProductDTO> moveOWCOutcomeProducts;
}
