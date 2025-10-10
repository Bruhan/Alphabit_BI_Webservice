package com.owner.process.usecases.wastage.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectInventoryDTO {
    String projectNo;
    double totalQty;
    String uom;
    String item;
    double processedQty;
    double pendingQty;
}
