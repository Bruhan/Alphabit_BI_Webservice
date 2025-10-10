package com.owner.process.usecases.sales_order.do_det_remarks;

import com.owner.process.persistence.models.DoDetRemarks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface DoDetRemarksRepository extends JpaRepository<DoDetRemarks,Long> {
	DoDetRemarks findByDoNoAndDoLineNoAndItem(String pk0,int pk1,String pk2);
	@Query(value="select * from ##plant##DODET_REMARKS " +
			"where DONO = :pk0 " +
			"order by DOLNNO ",nativeQuery = true)
	List<DoDetRemarks> findByDoNoOrderByDoLineNo(String pk0);

	@Transactional
	String deleteByDoNo(String doNo);

	List<DoDetRemarks> findByUniqueKey(String pk0);


}
