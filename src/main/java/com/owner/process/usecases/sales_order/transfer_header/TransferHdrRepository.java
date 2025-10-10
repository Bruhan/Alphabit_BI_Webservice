package com.owner.process.usecases.sales_order.transfer_header;

import com.owner.process.persistence.models.DoTransferHdr;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransferHdrRepository extends JpaRepository<DoTransferHdr,Long> {
	DoTransferHdr findByDoNo(String val);
}
