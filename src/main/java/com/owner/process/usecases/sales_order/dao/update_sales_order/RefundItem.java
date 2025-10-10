package com.owner.process.usecases.sales_order.dao.update_sales_order;

import lombok.Data;

@Data
public class RefundItem {
    private String sku;
    private Integer doLnNo;
    private long quantity;
}
