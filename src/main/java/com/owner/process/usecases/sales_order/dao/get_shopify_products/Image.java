package com.owner.process.usecases.sales_order.dao.get_shopify_products;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter @Setter
public class Image {
    private long id;
    @JsonProperty("product_id")
    private long productId;
    private int position;
    @JsonProperty("created_at")
    private Date createdAt;
    @JsonProperty("updated_at")
    private Date updatedAt;
    private String alt;
    private int width;
    private int height;
    private String src;
    @JsonProperty("variant_ids")
    private List<String> variantIds;
    @JsonProperty("admin_graphql_api_id")
    private String adminGraphqlApiId;
}