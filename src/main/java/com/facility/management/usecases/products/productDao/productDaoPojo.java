package com.facility.management.usecases.products.productDao;

public interface productDaoPojo {
    int getId();
    String getPlant();
    String getItem();
    String getItemDesc();
    String getCategory();
    String getDepartment();
    String getBrand();
    String getSubCategory();
    String getSalesUom();
    Double getStockQty();
    Double getOrderQty();
    Double getMaxOrderQty();
}
