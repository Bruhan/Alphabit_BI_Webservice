package com.owner.process.usecases.outlets_and_terminals.POSOutletsTerminals;

import com.owner.process.persistence.models.POSOutletsTerminals;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface POSOutletsTerminalsRepository extends JpaRepository<POSOutletsTerminals,Integer>{

    @Query(value = "SELECT TOP 1 * FROM ##plant##POSOUTLETSTERMINALS where OUTLET =?1 AND TERMINAL =?2", nativeQuery = true)
    POSOutletsTerminals findByOUTLETAndTERMINAL(String outlet,String terminal);

    @Query(value = "SELECT * FROM ##plant##POSOUTLETSTERMINALS where ISNULL(DEVICE_STATUS,'0') =?1 AND DEVICE_NAME =?2", nativeQuery = true)
    List<POSOutletsTerminals> findBydevicename(Short devicestatus,String devicename);

    @Query(value = "SELECT * FROM ##plant##POSOUTLETSTERMINALS where ISNULL(DEVICE_STATUS,'0') =?1 AND DEVICE_NAME =?2 AND TERMINAL =?3", nativeQuery = true)
    List<POSOutletsTerminals> findByDnameAndTerminal(Short devicestatus,String devicename,String terminal);

    List<POSOutletsTerminals> findByOUTLET(String outlet);

    @Query(value = "SELECT * FROM ##plant##POSOUTLETSTERMINALS where ISNULL(DEVICE_STATUS,'0') =?1 AND OUTLET =?2", nativeQuery = true)
    List<POSOutletsTerminals> findByoutletandstatus(Short devicestatus,String outlet);
}

