package com.owner.process.usecases.purchase_order.poDet;

import com.owner.process.persistence.models.PoDet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PoDetRepository extends JpaRepository<PoDet,Integer> {
    List<PoDet> findByPoNo(String pk0);
    List<PoDet> findByUniqueKey(String pk0);
}
