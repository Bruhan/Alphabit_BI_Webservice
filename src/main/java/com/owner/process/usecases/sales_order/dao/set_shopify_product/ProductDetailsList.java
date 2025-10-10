package com.owner.process.usecases.sales_order.dao.set_shopify_product;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class ProductDetailsList {

    private String plant;
    @JsonProperty("product_details")
    private List<ProductDetails> productDetails;

}