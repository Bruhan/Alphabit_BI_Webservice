package com.owner.process.usecases.sales_order.doAttachment;

import com.owner.process.persistence.models.DoAttachment;
import com.owner.process.persistence.models.PoAttachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoAttachmentRepository extends JpaRepository<DoAttachment,Integer> {
    List<DoAttachment> findByUniqueKey(String pk0);

}
