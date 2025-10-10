
package com.owner.process.usecases.sales_order.dao.shopify_location;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Locations {

    private long id;
    private String name;

    private String address1;
    private String address2;
    private String city;
    private String zip;
    private String province;
    private String country;
    private String phone;
    @JsonProperty("created_at")
    private Date createdAt;
    @JsonProperty("updated_at")
    private Date updatedAt;
    @JsonProperty("country_code")
    private String countryCode;
    @JsonProperty("country_name")
    private String countryName;
    @JsonProperty("province_code")
    private String provinceCode;
    private boolean legacy;
    private boolean active;
    @JsonProperty("admin_graphql_api_id")
    private String adminGraphqlApiId;
    @JsonProperty("localized_country_name")
    private String localizedCountryName;
    @JsonProperty("localized_province_name")
    private String localizedProvinceName;
}