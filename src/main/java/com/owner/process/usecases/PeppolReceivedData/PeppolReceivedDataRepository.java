package com.owner.process.usecases.PeppolReceivedData;

import com.owner.process.persistence.models.PEPPOL_RECEIVED_DATA;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PeppolReceivedDataRepository extends JpaRepository<PEPPOL_RECEIVED_DATA, Integer> {
    @Query(value = "Select * from ##plant##PEPPOL_RECEIVED_DATA where DOCID = ?1",nativeQuery = true)
    PEPPOL_RECEIVED_DATA findByDocId(String docid);

    @Query(value = "Select * from ##plant##PEPPOL_RECEIVED_DATA where DOCID = ?1 AND EVENT= ?2 AND RECEIVEDAT= ?3 AND BILLNO= ?4",nativeQuery = true)
    PEPPOL_RECEIVED_DATA findByDoc_id(String docid,String event,String receivedat,String billno);


}


