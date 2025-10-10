package com.owner.process.usecases.sales_order.do_det;

import com.owner.process.persistence.models.DoDet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface DoDetRepository extends JpaRepository<DoDet,Integer> {
	DoDet findByDoNoAndDoLineNo(String pk0,Integer pk1);
	List<DoDet> findByUniqueKey(String pk0);

	List<DoDet> findByDoNo(String pk0);
	@Query(value="select * from ##plant##doDet " +
			"where doNo = :pk0 " +
			"order by DoLnNo ",nativeQuery = true)
	List<DoDet> findByDoNoOrderByDoLineNo(String pk0);

	@Transactional
    String deleteByDoNoAndDoLineNo(String doNo, Integer i);

	@Transactional
	String deleteByDoNo(String doNo);
}
