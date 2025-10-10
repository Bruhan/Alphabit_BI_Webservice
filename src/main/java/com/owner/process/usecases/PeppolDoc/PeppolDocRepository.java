package com.owner.process.usecases.PeppolDoc;

import com.owner.process.persistence.models.PEPPOL_DOC_IDS;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PeppolDocRepository extends JpaRepository<PEPPOL_DOC_IDS, Integer> {
    @Query(value = "Select TOP 1 * from PEPPOL_DOC_IDS where DOC_ID = ?1",nativeQuery = true)
    PEPPOL_DOC_IDS findByDoc_id(String docid);


}


