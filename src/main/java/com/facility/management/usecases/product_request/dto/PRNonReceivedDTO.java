package com.facility.management.usecases.product_request.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PRNonReceivedDTO {
    private String item;
    private String projectNo;
    private double qty;
    private String uom;
    private double processedQty;
    private double balanceQty;
    private double receivedQty;
    private double nonReceivedQty;
}
