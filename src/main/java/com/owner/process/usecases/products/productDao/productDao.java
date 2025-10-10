package com.owner.process.usecases.products.productDao;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class productDao {
    private int id;
    private String plant;
    private String item;
    private String itemDesc;
    private String category;
    private String department;
    private String brand;
    private String subCategory;
    private String salesUom;
    private Double stockQty;
    private Double orderQty;
    private Double maxOrderQty;
}
