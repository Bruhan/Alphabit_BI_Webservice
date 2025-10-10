
package com.owner.process.usecases.sales_order.dao.create_sales_order;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class LineItems {

    private long id;
    @JsonProperty("variant_id")
    private long variantId;
    private String title;
    private long quantity;
    private String sku;
    @JsonProperty("variant_title")
    private String variantTitle;
    private String vendor;
    @JsonProperty("fulfillment_service")
    private String fulfillmentService;
    @JsonProperty("product_id")
    private long productId;
    @JsonProperty("requires_shipping")
    private boolean requiresShipping;
    private boolean taxable;
    @JsonProperty("gift_card")
    private boolean giftCard;
    private String name;
    @JsonProperty("variant_inventory_management")
    private String variantInventoryManagement;
    private List<Properties> properties;
    @JsonProperty("product_exists")
    private boolean productExists;
    @JsonProperty("fulfillable_quantity")
    private long fulfillableQuantity;
    private long grams;
    private String price;
    @JsonProperty("total_discount")
    private String totalDiscount;
    @JsonProperty("fulfillment_status")
    private String fulfillmentStatus;
    @JsonProperty("price_set")
    private PriceSet priceSet;
    @JsonProperty("total_discount_set")
    private TotalDiscountSet totalDiscountSet;
    @JsonProperty("discount_allocations")
    private List<String> discountAllocations;
    private List<Duties> duties;
    @JsonProperty("admin_graphql_api_id")
    private String adminGraphqlApiId;
    @JsonProperty("tax_lines")
    private List<TaxLines> taxLines;
    @JsonProperty("origin_location")
    private OriginLocation originLocation;
    @JsonProperty("destination_location")
    private DestinationLocation destinationLocation;

}