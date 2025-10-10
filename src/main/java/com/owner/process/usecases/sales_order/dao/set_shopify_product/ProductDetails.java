package com.owner.process.usecases.sales_order.dao.set_shopify_product;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class ProductDetails {
    @JsonProperty("product_id")
    private String productId;
    @JsonProperty("variant_id")
    private List<VariantId> variantId;
}