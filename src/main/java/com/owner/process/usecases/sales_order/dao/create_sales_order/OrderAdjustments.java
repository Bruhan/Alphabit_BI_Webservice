package com.owner.process.usecases.sales_order.dao.create_sales_order;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class OrderAdjustments {
    private long id;
    @JsonProperty("order_id")
    private long orderId;
    @JsonProperty("refund_id")
    private long refundId;
    private String amount;
    @JsonProperty("tax_amount")
    private String taxAmount;
    private String kind;
    private String reason;
    @JsonProperty("amount_set")
    private AmountSet amountSet;
    @JsonProperty("tax_amount_set")
    private TaxAmountSet taxAmountSet;
}
