package com.owner.process.usecases.sales_order.DoAttachmentApproval;

import com.owner.process.persistence.models.DoAttachmentApproval;
import com.owner.process.persistence.models.DoAttachmentApprovalIds;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoAttachmentApprovalRepository extends JpaRepository<DoAttachmentApproval, DoAttachmentApprovalIds> {
    List<DoAttachmentApproval> findByUniqueKey(String pk0);
}
