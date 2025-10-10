package com.owner.process.usecases.purchase_order.PoDetRemarks;

import com.owner.process.persistence.models.PoAttachment;
import com.owner.process.persistence.models.PoDetRemarks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PoDetRemarksRepository extends JpaRepository<PoDetRemarks,Integer> {
    List<PoDetRemarks> findByPoNo(String pk0);
    List<PoDetRemarks> findByUniqueKey(String pk0);
}
