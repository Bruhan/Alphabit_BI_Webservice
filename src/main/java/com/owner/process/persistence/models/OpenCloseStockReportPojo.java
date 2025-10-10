package com.owner.process.persistence.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OpenCloseStockReportPojo {
    private String plant;
    private String item;
    private String itemDesc;
    private String category;
    private String subCategory;
    private String brand;
    private String department;
    private String uom;
    private float openingStockQty;
    private float totalReceivedQty;
    private float totalIssuedQty;
    private float closingStock;
    private float averageCost;
    private float price;
    private float totalCost;
    private float totalPrice;
    private float laterReceveivedQty;
    private float laterIssuedQty;
    private double stockOnHand;
}
