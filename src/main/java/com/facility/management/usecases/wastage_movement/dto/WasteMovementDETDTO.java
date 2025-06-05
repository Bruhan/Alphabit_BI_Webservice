package com.facility.management.usecases.wastage_movement.dto;

import com.facility.management.usecases.wastage_movement.enums.WastageType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class WasteMovementDETDTO {
    private Integer id;
    private String customerId;
    private String customerName;
    private String destination;
    private String destinationType;
    private WastageType wastageType;
    private double qty;
    private String uom;
    private String deliveryChallanNo;
    private Short isDcSigned;
    private String authorizedSign;
    private List<WasteMovementInorganicProductDTO> wasteMovementInorganicProductList;
    private List<WasteMovementOWCOutcomeDTO> wasteMovementOWCOutcomeList;
}
