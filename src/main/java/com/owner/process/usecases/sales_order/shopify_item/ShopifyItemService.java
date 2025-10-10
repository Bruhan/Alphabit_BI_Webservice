package com.owner.process.usecases.sales_order.shopify_item;

import com.owner.process.persistence.models.ShopifyItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShopifyItemService {
	@Autowired
	ShopifyItemRepository shopifyItemRepository;

	public ShopifyItem getAllShopifyItemPk(String pk0, String pk1) throws Exception {
		ShopifyItem getVal;
		try {
			getVal = shopifyItemRepository.findByPlantAndSku(pk0, pk1);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return getVal;
	}

	public String checkShopifyItemPk(String pk0, String pk1) throws Exception {
		try {
			ShopifyItem getVal = shopifyItemRepository.findByPlantAndSku(pk0, pk1);
			if (getVal == null)
				return "1";
		} catch (Exception e) {
			throw new Exception("SQL Error!!!");
		}
		return "0";
	}

	public String setShopifyItemDetails(ShopifyItem val) throws Exception {
		try {
			shopifyItemRepository.save(val);
		} catch (Exception e) {
			throw new Exception("SQL Error!!!");
		}
		return "1";
	}
}
