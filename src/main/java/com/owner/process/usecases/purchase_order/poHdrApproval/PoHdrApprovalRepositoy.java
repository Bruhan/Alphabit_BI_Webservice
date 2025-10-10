package com.owner.process.usecases.purchase_order.poHdrApproval;

import com.owner.process.persistence.models.PoHdrApproval;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PoHdrApprovalRepositoy extends JpaRepository<PoHdrApproval,String> {
    PoHdrApproval findByUniqueKey(String pk0);
}
