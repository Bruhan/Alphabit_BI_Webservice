package com.owner.process.usecases.sales_order.shopify_config;

import com.owner.process.helpers.exception.custom.ResourceNotFoundException;
import com.owner.process.persistence.models.ShopifyConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShopifyConfigService {
	@Autowired
	ShopifyConfigRepository shopifyConfigRepository;

	public String checkShopifyConfigPk(String pk0) throws Exception {
		try {
			ShopifyConfig getVal = shopifyConfigRepository.findByDomainName(pk0);
			if (getVal == null)
				return "1";
		} catch (Exception e) {
			throw new Exception("SQL Error!!!");
		}
		return "0";
	}

	public String setShopifyConfigDetails(ShopifyConfig val) throws Exception {
		try {
			shopifyConfigRepository.save(val);
		} catch (Exception e) {
			throw new Exception("SQL Error!!!");
		}
		return "1";
	}

	public String getPlantFromDomainName(String pk0) throws Exception {
		String getVal;
		try {
			getVal = shopifyConfigRepository.findByDomainName(pk0).getPlant();
			if (getVal == null)
				return "1";
		} catch (Exception e) {
            throw new Exception(e.getMessage());
        }
		return getVal;
	}

	public ShopifyConfig getAllFromPlant(String pk0) throws Exception {
		ShopifyConfig getVal;
		try {
			getVal = shopifyConfigRepository.findByPlant(pk0);
			if (getVal == null)
				throw new ResourceNotFoundException();
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return getVal;
	}

}
