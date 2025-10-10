package com.owner.process.usecases.sales_order.dao.create_sales_order;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

@Data
public class Transactions {
    private long id;
    @JsonProperty("order_id")
    private long orderId;
    private String amount;
    private String kind;
    private String gateway;
    private String status;
    private String message;
    @JsonProperty("created_at")
    private Date createdAt;
    private boolean test;
    private String authorization;
    private String currency;
    @JsonProperty("location_id")
    private String locationId;
    @JsonProperty("user_id")
    private String userId;
    @JsonProperty("parent_id")
    private long parentId;
    @JsonProperty("device_id")
    private String deviceId;
    private Receipt receipt;
    @JsonProperty("error_code")
    private String errorCode;
    @JsonProperty("source_name")
    private String sourceName;
    @JsonProperty("admin_graphql_api_id")
    private String adminGraphqlApiId;
    @JsonProperty("processed_at")
    private Date processedAt;
}
