package com.owner.process.usecases.sales_order.alternate_item_mapping;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.owner.process.persistence.models.AlternateItemMapping;

@Service
public class AlternateItemMappingService {
	@Autowired
	AlternateItemMappingRepository alternateItemMappingRepository;
	public String checkAlternateItemMappingPk(String pk0) throws Exception {
		try {
			AlternateItemMapping getVal = alternateItemMappingRepository.findByItem(pk0);
			if(getVal == null)
				return "1";
		} catch (Exception e) {
			throw new Exception("SQL Error!!!");
		}
	return "0";
	}
	public String setAlternateItemMappingDetails(AlternateItemMapping val) throws Exception {
		try {
			alternateItemMappingRepository.save(val);
		} catch (Exception e) {
			throw new Exception("SQL Error!!!");
		}
		return "1";
	}
}
