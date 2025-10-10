package com.owner.process.usecases.sales_order.shopify_config;

import com.owner.process.persistence.models.ShopifyConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopifyConfigRepository extends JpaRepository<ShopifyConfig, Long> {
    ShopifyConfig findByDomainName(String pk0);

    ShopifyConfig findByPlant(String pk0);
}
