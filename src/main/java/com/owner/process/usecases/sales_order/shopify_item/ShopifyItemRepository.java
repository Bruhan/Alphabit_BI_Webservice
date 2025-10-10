package com.owner.process.usecases.sales_order.shopify_item;

import com.owner.process.persistence.models.ShopifyItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopifyItemRepository extends JpaRepository<ShopifyItem, Long> {
	ShopifyItem findByPlantAndSku(String pk0, String pk1);
	ShopifyItem findByProductIdAndSkuAndVariantId(String productId, String sku, String variantId);
}
