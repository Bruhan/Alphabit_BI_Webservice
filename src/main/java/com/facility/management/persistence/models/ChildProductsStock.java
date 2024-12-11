package com.facility.management.persistence.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChildProductsStock {
    private String item;
    private String itemDescription;
    private String unitMo;
    private float quantityOr;
    private Double stockQuantity;
    private Double parentQuantity;
}
