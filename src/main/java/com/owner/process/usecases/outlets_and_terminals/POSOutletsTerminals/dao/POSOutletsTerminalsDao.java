package com.owner.process.usecases.outlets_and_terminals.POSOutletsTerminals.dao;

import com.owner.process.persistence.models.POSOutletsTerminals;

import java.util.List;

public interface POSOutletsTerminalsDao {

    POSOutletsTerminals findByOUTLETAndTERMINAL(String plant, String outlet, String terminal);

    List<POSOutletsTerminals> findBydevicename(String plant, Short devicestatus, String devicename);

    List<POSOutletsTerminals> findByDnameAndTerminal(String plant, Short devicestatus,String devicename,String terminal);

    List<POSOutletsTerminals> findByOUTLET(String plant, String outlet);

    List<POSOutletsTerminals> findByoutletandstatus(String plant, Short devicestatus,String outlet);

    void addPOSOutletsTerminals(POSOutletsTerminals posOutletsTerminals);

}
