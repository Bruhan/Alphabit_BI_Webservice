package com.owner.process.usecases.purchase_order.PoAttachmentApproval;

import com.owner.process.persistence.models.PoAttachmentApproval;
import com.owner.process.persistence.models.PoAttachmentApprovalIds;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PoAttachmentApprovalRepository extends JpaRepository<PoAttachmentApproval, PoAttachmentApprovalIds> {
    List<PoAttachmentApproval> findByPoNo(String pk0);
    List<PoAttachmentApproval> findByUniqueKey(String pk0);
}
