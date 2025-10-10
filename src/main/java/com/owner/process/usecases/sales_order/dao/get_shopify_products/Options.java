package com.owner.process.usecases.sales_order.dao.get_shopify_products;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class Options {
    private long id;
    @JsonProperty("product_id")
    private long productId;
    private String name;
    private int position;
    private List<String> values;
}