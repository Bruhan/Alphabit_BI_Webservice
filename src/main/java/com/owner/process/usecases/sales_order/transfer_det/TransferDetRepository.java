package com.owner.process.usecases.sales_order.transfer_det;

import com.owner.process.persistence.models.DoTransferDet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface TransferDetRepository extends JpaRepository<DoTransferDet, Long> {
	DoTransferDet findByDoNoAndDoLineNo(String pk1, int pk2);

	@Transactional
	String deleteByDoNoAndDoLineNo(String doNo, Integer i);

	@Transactional
	String deleteByDoNo(String doNo);
}
