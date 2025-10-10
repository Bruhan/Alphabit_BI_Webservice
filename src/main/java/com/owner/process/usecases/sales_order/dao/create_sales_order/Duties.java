package com.owner.process.usecases.sales_order.dao.create_sales_order;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class Duties {
    private String id;
    @JsonProperty("harmonized_system_code")
    private String harmonizedSystemCode;
    @JsonProperty("country_code_of_origin")
    private String countryCodeOfOrigin;
    @JsonProperty("shop_money")
    private ShopMoney shopMoney;
    @JsonProperty("presentment_money")
    private PresentmentMoney presentmentMoney;
    @JsonProperty("tax_lines")
    private List<TaxLines> taxLines;
    @JsonProperty("admin_graphql_api_id")
    private String adminGraphqlApiId;
}
