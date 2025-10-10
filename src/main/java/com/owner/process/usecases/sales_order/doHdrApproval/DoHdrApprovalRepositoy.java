package com.owner.process.usecases.sales_order.doHdrApproval;

import com.owner.process.persistence.models.DoHdrApproval;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoHdrApprovalRepositoy extends JpaRepository<DoHdrApproval,String> {
    DoHdrApproval findByUniqueKey(String pk0);

}
