package com.owner.process.persistence.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OutletIssuedReportPojo {
    private String plant;
    private String item;
    private String itemDesc;
    private String category;
    private String subCategory;
    private String brand;
    private String department;
    private String uom;
    private float totalIssuedQty;
    private float price;
    private float totalPrice;
}
