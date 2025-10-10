package com.owner.process.usecases.hq_process.prod_bom_mst;

import com.owner.process.persistence.models.ProdBOMMst;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdBOMMstRepository extends JpaRepository<ProdBOMMst,Long> {
    List<ProdBOMMst> findByPitemAndBomType(String item,String type);
}
