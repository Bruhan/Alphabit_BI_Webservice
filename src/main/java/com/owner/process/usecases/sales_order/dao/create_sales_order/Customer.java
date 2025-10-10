
package com.owner.process.usecases.sales_order.dao.create_sales_order;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
public class Customer {

    private long id;
    private String email;
    @JsonProperty("accepts_marketing")
    private boolean acceptsMarketing;
    @JsonProperty("created_at")
    private Date createdAt;
    @JsonProperty("updated_at")
    private Date updatedAt;
    @JsonProperty("first_name")
    private String firstName;
    @JsonProperty("last_name")
    private String lastName;
    @JsonProperty("orders_count")
    private long ordersCount;
    private String state;
    @JsonProperty("total_spent")
    private String totalSpent;
    @JsonProperty("last_order_id")
    private long lastOrderId;
    private String note;
    @JsonProperty("verified_email")
    private boolean verifiedEmail;
    @JsonProperty("multipass_identifier")
    private String multipassIdentifier;
    @JsonProperty("tax_exempt")
    private boolean taxExempt;
    @JsonProperty("tax_exemptions")
    private TaxExceptions taxExemptions;
    private String phone;
    private String tags;
    @JsonProperty("last_order_name")
    private String lastOrderName;
    private String currency;
    private Addresses addresses;
    @JsonProperty("accepts_marketing_updated_at")
    private Date acceptsMarketingUpdatedAt;
    @JsonProperty("marketing_opt_in_level")
    private String marketingOptInLevel;
    @JsonProperty("admin_graphql_api_id")
    private String adminGraphqlApiId;
    @JsonProperty("default_address")
    private DefaultAddress defaultAddress;
}