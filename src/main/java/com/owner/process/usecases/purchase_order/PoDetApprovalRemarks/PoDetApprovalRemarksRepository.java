package com.owner.process.usecases.purchase_order.PoDetApprovalRemarks;

import com.owner.process.persistence.models.PoAttachment;
import com.owner.process.persistence.models.PoDetApprovalRemarks;
import com.owner.process.persistence.models.PoDetApprovalRemarksIds;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PoDetApprovalRemarksRepository extends JpaRepository<PoDetApprovalRemarks, PoDetApprovalRemarksIds> {
    List<PoDetApprovalRemarks> findByPoNo(String pk0);
    List<PoDetApprovalRemarks> findByUniqueKey(String pk0);
}
