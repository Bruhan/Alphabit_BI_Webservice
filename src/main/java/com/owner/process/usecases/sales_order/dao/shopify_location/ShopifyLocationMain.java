
package com.owner.process.usecases.sales_order.dao.shopify_location;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class ShopifyLocationMain implements Serializable {

    private List<Locations> locations;

}