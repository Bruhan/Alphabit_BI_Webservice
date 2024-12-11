package com.facility.management.usecases.products.productDao;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemMstDao {
    private int id;
    private String item;
    private String nonStackFlag;
    private String itemDescription;
    private String stackUom;
    private float netWeight;
    private float grossWeight;
    private String productClassId;
    private String ItemType;
    private String productBrandId;
    private String purchaseUom;
    private float cost;
    private String salesUom;
    private float minsPrice;
    private float unitPrice;
    private String inventoryUom;
    private float stackQuantity;
    private float maximumStackQuantity;
    private String catalogPath;
    private String itemLocation;
}
