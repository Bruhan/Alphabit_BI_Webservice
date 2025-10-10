package com.owner.process.usecases.purchase_order.poDetApproval;

import com.owner.process.persistence.models.PoDetApproval;
import com.owner.process.persistence.models.PoDetApprovalIds;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PoDetApprovalRepository extends JpaRepository<PoDetApproval, PoDetApprovalIds>{
    List<PoDetApproval> findByUniqueKey(String pk0);
}
