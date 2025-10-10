package com.owner.process.usecases.sales_order.dao.set_shopify_product;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class VariantId {
    @JsonProperty("id")
    private String id;
}