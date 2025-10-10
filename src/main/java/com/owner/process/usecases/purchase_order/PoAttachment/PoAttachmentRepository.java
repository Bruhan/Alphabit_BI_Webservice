package com.owner.process.usecases.purchase_order.PoAttachment;

import com.owner.process.persistence.models.PoAttachment;
import com.owner.process.persistence.models.PoDet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PoAttachmentRepository extends JpaRepository<PoAttachment,Integer> {
    List<PoAttachment> findByPoNo(String pk0);
    List<PoAttachment> findByUniqueKey(String pk0);
}
