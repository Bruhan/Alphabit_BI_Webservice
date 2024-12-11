package com.facility.management.usecases.products.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDTO {
    private int id;
    private String plant;
    private String item;
    private String itemDesc;
    private String category;
    private String subCategory;
    private String stkUom;

}
