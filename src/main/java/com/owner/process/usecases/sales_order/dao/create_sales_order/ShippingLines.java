package com.owner.process.usecases.sales_order.dao.create_sales_order;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;


public class ShippingLines {

    private long id;
    private String title;
    private String price;
    private String code;
    private String source;
    private String phone;
    @JsonProperty("requested_fulfillment_service_id")
    private String requestedFulfillmentServiceId;
    @JsonProperty("delivery_category")
    private String deliveryCategory;
    @JsonProperty("carrier_identifier")
    private String carrierIdentifier;
    @JsonProperty("discounted_price")
    private String discountedPrice;
    @JsonProperty("price_set")
    private PriceSet priceSet;
    @JsonProperty("discounted_price_set")
    private DiscountedPriceSet discountedPriceSet;
    @JsonProperty("discount_allocations")
    private List<String> discountAllocations;
    @JsonProperty("tax_lines")
    private List<String> taxLines;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRequestedFulfillmentServiceId() {
        return requestedFulfillmentServiceId;
    }

    public void setRequestedFulfillmentServiceId(String requestedFulfillmentServiceId) {
        this.requestedFulfillmentServiceId = requestedFulfillmentServiceId;
    }

    public String getDeliveryCategory() {
        return deliveryCategory;
    }

    public void setDeliveryCategory(String deliveryCategory) {
        this.deliveryCategory = deliveryCategory;
    }

    public String getCarrierIdentifier() {
        return carrierIdentifier;
    }

    public void setCarrierIdentifier(String carrierIdentifier) {
        this.carrierIdentifier = carrierIdentifier;
    }

    public String getDiscountedPrice() {
        return discountedPrice;
    }

    public void setDiscountedPrice(String discountedPrice) {
        this.discountedPrice = discountedPrice;
    }

    public PriceSet getPriceSet() {
        return priceSet;
    }

    public void setPriceSet(PriceSet priceSet) {
        this.priceSet = priceSet;
    }

    public DiscountedPriceSet getDiscountedPriceSet() {
        return discountedPriceSet;
    }

    public void setDiscountedPriceSet(DiscountedPriceSet discountedPriceSet) {
        this.discountedPriceSet = discountedPriceSet;
    }

    public List<String> getDiscountAllocations() {
        return discountAllocations;
    }

    public void setDiscountAllocations(List<String> discountAllocations) {
        this.discountAllocations = discountAllocations;
    }

    public List<String> getTaxLines() {
        return taxLines;
    }

    public void setTaxLines(List<String> taxLines) {
        this.taxLines = taxLines;
    }

}