package com.owner.process.usecases.sales_order.dao.create_sales_order;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OriginalTotalDutiesSet {
    @JsonProperty("shop_money")
    private ShopMoney shopMoney;
    @JsonProperty("presentment_money")
    private PresentmentMoney presentmentMoney;
}
