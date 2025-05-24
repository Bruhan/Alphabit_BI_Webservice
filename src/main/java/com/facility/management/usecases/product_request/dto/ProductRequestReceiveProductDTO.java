package com.facility.management.usecases.product_request.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductRequestReceiveProductDTO {
    private int id;
    private String item;
    private String itemDesc;
    private double quantity;
    private double processedQty;
    private double balanceQty;
    private double receivedQty;
    private double nonReceivedQty;
    private String uom;
}
