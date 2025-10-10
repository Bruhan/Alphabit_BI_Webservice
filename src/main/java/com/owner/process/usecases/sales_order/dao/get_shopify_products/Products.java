package com.owner.process.usecases.sales_order.dao.get_shopify_products;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter @Setter
public class Products {
    private long id;
    private String title;
    @JsonProperty("body_html")
    private String bodyHtml;
    private String vendor;
    @JsonProperty("product_type")
    private String productType;
    @JsonProperty("created_at")
    private Date createdAt;
    private String handle;
    @JsonProperty("updated_at")
    private Date updatedAt;
    @JsonProperty("published_at")
    private Date publishedAt;
    @JsonProperty("template_suffix")
    private String templateSuffix;
    @JsonProperty("published_scope")
    private String publishedScope;
    private String tags;
    @JsonProperty("admin_graphql_api_id")
    private String adminGraphqlApiId;
    private List<Variants> variants;
    private List<Options> options;
    private List<Images> images;
    private Image image;
    //newly added
    private String status;
}