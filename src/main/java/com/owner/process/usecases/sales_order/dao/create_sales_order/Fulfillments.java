package com.owner.process.usecases.sales_order.dao.create_sales_order;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

@Data
public class Fulfillments {
    @JsonProperty("created_at")
    private Date createdAt;
    private long id;

    @JsonProperty("order_id")
    private long orderId;
    private String status;
    @JsonProperty("tracking_company")
    private String trackingCompany;
    @JsonProperty("tracking_number")
    private String trackingNumber;
    @JsonProperty("updated_at")
    private Date updatedAt;
}
