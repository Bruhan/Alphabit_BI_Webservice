package com.owner.process.usecases.sales_order.doDetApproval;

import com.owner.process.persistence.models.DoDetApproval;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoDetApprovalRepository extends JpaRepository<DoDetApproval,String> {
    List<DoDetApproval> findByUniqueKey(String pk0);
}
