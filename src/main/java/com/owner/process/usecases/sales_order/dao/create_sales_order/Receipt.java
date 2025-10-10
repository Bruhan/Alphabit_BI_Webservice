package com.owner.process.usecases.sales_order.dao.create_sales_order;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Receipt {
    @JsonProperty("paid_amount")
    private String paidAmount;
}
