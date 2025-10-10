package com.owner.process.usecases.sales_order.inventory_master;

import com.owner.process.persistence.models.InvMst;
import com.owner.process.usecases.sales_order.salessummary.salesSummaryPojo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvMstRepository extends JpaRepository<InvMst,Integer> {
	InvMst findByIdAndItemAndLocationAndUserFieldFour(Integer pk0,String pk1,String pk2,String pk3);

	@Query(value="SELECT ISNULL(SUM(QTY),0) AS QTY FROM ##plant##INVMST WHERE ITEM=?1 AND LOC=?2 " +
			"AND USERFLD4=?3",nativeQuery = true)
	double getTQtyByItemLocBatch(String pk0,String pk1,String pk2);

	InvMst findByItemAndLocationAndUserFieldFour(String pk0,String pk1,String pk2);

	@Query(value="SELECT SUM(QTY) AS QTY FROM ##plant##INVMST WHERE ITEM=?1 AND (LOC!=?2 OR LOC!=?3)",nativeQuery = true)
	double getTQtyByItemForStock(String pk0,String pk1,String pk2);

	@Query(value="SELECT ISNULL(SUM(QTY),0) AS QTY FROM ##plant##INVMST WHERE ITEM=?1",nativeQuery = true)
	double getTQtyByItem(String pk0);


}
