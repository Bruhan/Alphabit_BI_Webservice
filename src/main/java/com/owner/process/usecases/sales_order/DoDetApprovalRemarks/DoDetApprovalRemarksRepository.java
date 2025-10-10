package com.owner.process.usecases.sales_order.DoDetApprovalRemarks;

import com.owner.process.persistence.models.DoDetApprovalRemarks;
import com.owner.process.persistence.models.DoDetApprovalRemarksIds;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoDetApprovalRemarksRepository extends JpaRepository<DoDetApprovalRemarks, DoDetApprovalRemarksIds> {
    List<DoDetApprovalRemarks> findByUniqueKey(String pk0);
}
