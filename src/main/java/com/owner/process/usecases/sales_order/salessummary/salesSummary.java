package com.owner.process.usecases.sales_order.salessummary;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class salesSummary {
    private String date;
    private String dono;
    private int itemCount;
    private String orderStatus;
    private String custCode;
    private String custName;
}
