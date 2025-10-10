package com.owner.process.usecases.sales_order.dao.variant_id;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.List;

public class Variant {

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
    @JsonProperty("presentment_prices")
    private List<PresentmentPrices> presentmentPrices;
    @JsonProperty("requires_shipping")
    private boolean requiresShipping;
    @JsonProperty("admin_graphql_api_id")
    private String adminGraphqlApiId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public long getPosition() {
        return position;
    }

    public void setPosition(long position) {
        this.position = position;
    }

    public String getInventoryPolicy() {
        return inventoryPolicy;
    }

    public void setInventoryPolicy(String inventoryPolicy) {
        this.inventoryPolicy = inventoryPolicy;
    }

    public String getCompareAtPrice() {
        return compareAtPrice;
    }

    public void setCompareAtPrice(String compareAtPrice) {
        this.compareAtPrice = compareAtPrice;
    }

    public String getFulfillmentService() {
        return fulfillmentService;
    }

    public void setFulfillmentService(String fulfillmentService) {
        this.fulfillmentService = fulfillmentService;
    }

    public String getInventoryManagement() {
        return inventoryManagement;
    }

    public void setInventoryManagement(String inventoryManagement) {
        this.inventoryManagement = inventoryManagement;
    }

    public String getOption1() {
        return option1;
    }

    public void setOption1(String option1) {
        this.option1 = option1;
    }

    public String getOption2() {
        return option2;
    }

    public void setOption2(String option2) {
        this.option2 = option2;
    }

    public String getOption3() {
        return option3;
    }

    public void setOption3(String option3) {
        this.option3 = option3;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public boolean getTaxable() {
        return taxable;
    }

    public void setTaxable(boolean taxable) {
        this.taxable = taxable;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public long getGrams() {
        return grams;
    }

    public void setGrams(long grams) {
        this.grams = grams;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public long getWeight() {
        return weight;
    }

    public void setWeight(long weight) {
        this.weight = weight;
    }

    public String getWeightUnit() {
        return weightUnit;
    }

    public void setWeightUnit(String weightUnit) {
        this.weightUnit = weightUnit;
    }

    public long getInventoryItemId() {
        return inventoryItemId;
    }

    public void setInventoryItemId(long inventoryItemId) {
        this.inventoryItemId = inventoryItemId;
    }

    public long getInventoryQuantity() {
        return inventoryQuantity;
    }

    public void setInventoryQuantity(long inventoryQuantity) {
        this.inventoryQuantity = inventoryQuantity;
    }

    public long getOldInventoryQuantity() {
        return oldInventoryQuantity;
    }

    public void setOldInventoryQuantity(long oldInventoryQuantity) {
        this.oldInventoryQuantity = oldInventoryQuantity;
    }

    public List<PresentmentPrices> getPresentmentPrices() {
        return presentmentPrices;
    }

    public void setPresentmentPrices(List<PresentmentPrices> presentmentPrices) {
        this.presentmentPrices = presentmentPrices;
    }

    public boolean getRequiresShipping() {
        return requiresShipping;
    }

    public void setRequiresShipping(boolean requiresShipping) {
        this.requiresShipping = requiresShipping;
    }

    public String getAdminGraphqlApiId() {
        return adminGraphqlApiId;
    }

    public void setAdminGraphqlApiId(String adminGraphqlApiId) {
        this.adminGraphqlApiId = adminGraphqlApiId;
    }

}