package com.facility.management.usecases.product_request.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductRequestReceiveProductDTO {
    private int id;
    private String item;
    private String itemDesc;
    private double qty;
    private String uom;
}
