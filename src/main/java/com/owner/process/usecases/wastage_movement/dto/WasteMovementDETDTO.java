package com.owner.process.usecases.wastage_movement.dto;

import com.owner.process.usecases.wastage_movement.enums.WastageType;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class WasteMovementDETDTO {
    private String customerId;
    private String customerName;
    private String destination;
    private String destinationType;
    private WastageType wastageType;
    private double qty;
    private String uom;
    private List<WasteMovementInorganicProductDTO> wasteMovementInorganicProductList;
    private List<WasteMovementOWCOutcomeDTO> wasteMovementOWCOutcomeList;
}
