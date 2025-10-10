package com.owner.process.usecases.sales_order.dao.create_sales_order;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class DiscountCodes {
    private String code;
    private String type;
    private String amount;
}
