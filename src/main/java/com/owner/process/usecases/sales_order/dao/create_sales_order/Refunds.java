package com.owner.process.usecases.sales_order.dao.create_sales_order;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class Refunds {
    private long id;
    @JsonProperty("admin_graphql_api_id")
    private String adminGraphqlApiId;
    @JsonProperty("order_id")
    private long orderId;
    @JsonProperty("created_at")
    private Date createdAt;
    private String note;
    private Boolean restock;
    @JsonProperty("user_id")
    private String userId;
    @JsonProperty("processed_at")
    private Date processedAt;
    @JsonProperty("refund_line_items")
    private List<RefundLineItems> refundLineItems;
    private List<Transactions> transactions;
    @JsonProperty("order_adjustments")
    private List<OrderAdjustments> orderAdjustments;
    private List<Duties> duties;
    @JsonProperty("total_duties_set")
    private TotalDutiesSet totalDutiesSet;
}
