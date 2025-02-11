package com.facility.management.usecases.wastage_movement.dto;

import com.facility.management.usecases.wastage_movement.enums.WastageType;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class WasteMovementDETOutDTO {
    private Integer id;
    private String customerId;
    private String customerName;
    private String destination;
    private String destinationType;
    private WastageType wastageType;
    private double qty;
    private String uom;
    private List<WasteMovementInorganicProductOutDTO> wasteMovementInorganicProductList;
    private List<WasteMovementOWCOutcomeOutDTO> wasteMovementOWCOutcomeList;
}
