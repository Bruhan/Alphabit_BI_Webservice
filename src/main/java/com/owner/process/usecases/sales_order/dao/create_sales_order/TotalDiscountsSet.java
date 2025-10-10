
package com.owner.process.usecases.sales_order.dao.create_sales_order;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TotalDiscountsSet {

    @JsonProperty("shop_money")
    private ShopMoney shopMoney;
    @JsonProperty("presentment_money")
    private PresentmentMoney presentmentMoney;

    public ShopMoney getShopMoney() {
        return shopMoney;
    }

    public void setShopMoney(ShopMoney shopMoney) {
        this.shopMoney = shopMoney;
    }

    public PresentmentMoney getPresentmentMoney() {
        return presentmentMoney;
    }

    public void setPresentmentMoney(PresentmentMoney presentmentMoney) {
        this.presentmentMoney = presentmentMoney;
    }

}