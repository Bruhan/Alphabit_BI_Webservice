
package com.owner.process.usecases.sales_order.dao.create_sales_order;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TaxLines {

    private String title;
    private String price;
    private double rate;
    @JsonProperty("price_set")
    private PriceSet priceSet;

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

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public PriceSet getPriceSet() {
        return priceSet;
    }

    public void setPriceSet(PriceSet priceSet) {
        this.priceSet = priceSet;
    }

}