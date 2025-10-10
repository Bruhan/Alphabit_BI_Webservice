package com.owner.process.usecases.sales_order.dao.get_shopify_products;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
public class Variants {
    private long id;
    @JsonProperty("product_id")
    private long productId;
    private String title;
    private String price;
    private String sku;
    private long position;
    @JsonProperty("inventory_policy")
    private String inventoryPolicy;
    @JsonProperty("compare_at_price")
    private String compareAtPrice;
    @JsonProperty("fulfillment_service")
    private String fulfillmentService;
    @JsonProperty("inventory_management")
    private String inventoryManagement;
    private String option1;
    private String option2;
    private String option3;
    @JsonProperty("created_at")
    private Date createdAt;
    @JsonProperty("updated_at")
    private Date updatedAt;
    private boolean taxable;
    private String barcode;
    private long grams;
    @JsonProperty("image_id")
    private String imageId;
    private long weight;
    @JsonProperty("weight_unit")
    private String weightUnit;
    @JsonProperty("inventory_item_id")
    private long inventoryItemId;
    @JsonProperty("inventory_quantity")
    private long inventoryQuantity;
    @JsonProperty("old_inventory_quantity")
    private long oldInventoryQuantity;
    @JsonProperty("requires_shipping")
    private boolean requiresShipping;
    @JsonProperty("admin_graphql_api_id")
    private String adminGraphqlApiId;
}