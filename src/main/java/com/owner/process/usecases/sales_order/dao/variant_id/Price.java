package com.owner.process.usecases.sales_order.dao.variant_id;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Price {

    private String amount;
    @JsonProperty("currency_code")
    private String currencyCode;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

}