package com.owner.process.usecases.sales_order.dao.create_sales_order;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class DiscountApplications {
    private String type;
    private String title;
    private String description;
    private String value;
    @JsonProperty("value_type")
    private String valueType;
    @JsonProperty("allocation_method")
    private String allocationMethod;
    @JsonProperty("target_selection")
    private String targetSelection;
    @JsonProperty("target_type")
    private String targetType;
}
