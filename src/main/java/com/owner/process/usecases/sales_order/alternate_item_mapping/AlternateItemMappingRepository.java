package com.owner.process.usecases.sales_order.alternate_item_mapping;

import com.owner.process.persistence.models.AlternateItemMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlternateItemMappingRepository extends JpaRepository<AlternateItemMapping,Long> {
	AlternateItemMapping findByItem(String pk0);
}
