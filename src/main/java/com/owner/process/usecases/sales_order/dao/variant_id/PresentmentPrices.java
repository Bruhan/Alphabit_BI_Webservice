package com.owner.process.usecases.sales_order.dao.variant_id;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PresentmentPrices {

    private Price price;
    @JsonProperty("compare_at_price")
    private CompareAtPrice compareAtPrice;

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public CompareAtPrice getCompareAtPrice() {
        return compareAtPrice;
    }

    public void setCompareAtPrice(CompareAtPrice compareAtPrice) {
        this.compareAtPrice = compareAtPrice;
    }

}