package com.owner.process.usecases.sales_order.dao.create_sales_order;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RefundLineItems {
    private long id;
    @JsonProperty("line_item")
    private LineItems lineItem;
    @JsonProperty("line_item_id")
    private long lineItemId;
    private int quantity;
    @JsonProperty("location_id")
    private long locationId;
    @JsonProperty("restock_type")
    private String restockType;
    private double subtotal;
    @JsonProperty("total_tax")
    private double totalTax;
    @JsonProperty("subtotal_set")
    private SubtotalSet subtotalSet;
    @JsonProperty("total_tax_set")
    private TotalTaxSet totalTaxSet;
}
