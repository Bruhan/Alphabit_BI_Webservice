package com.owner.process.usecases.outlets_and_terminals.POSOutletsTerminals;

import com.owner.process.persistence.models.POSOutletsTerminals;
import com.owner.process.usecases.outlets_and_terminals.POSOutletsTerminals.dao.POSOutletsTerminalsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class POSOutletsTerminalsService {

    @Autowired
    POSOutletsTerminalsDao posOutletsTerminalsDao;

    public POSOutletsTerminals getByoutletsAndTerminal(String plant, String outlet,String terminal) throws Exception {
        POSOutletsTerminals pOSOutletsTerminals;
        try {
            pOSOutletsTerminals = posOutletsTerminalsDao.findByOUTLETAndTERMINAL(plant, outlet,terminal);
        }catch (Exception e){
            throw new Exception(e);
        }
        return pOSOutletsTerminals;
    }

    public List<POSOutletsTerminals> getByDeviceName(String plant, Short devicestatus,String devicename) throws Exception {
        List<POSOutletsTerminals> pOSOutletsTerminals;
        try {
            pOSOutletsTerminals = posOutletsTerminalsDao.findBydevicename(plant, devicestatus,devicename);
        }catch (Exception e){
            throw new Exception(e);
        }
        return pOSOutletsTerminals;
    }

    public List<POSOutletsTerminals> getByDeviceNameTerminal(String plant, Short devicestatus,String devicename,String terminal) throws Exception {
        List<POSOutletsTerminals> pOSOutletsTerminals;
        try {
            pOSOutletsTerminals = posOutletsTerminalsDao.findByDnameAndTerminal(plant, devicestatus,devicename,terminal);
        }catch (Exception e){
            throw new Exception(e);
        }
        return pOSOutletsTerminals;
    }

    public List<POSOutletsTerminals> getByoutlets(String plant, String outlet) throws Exception {
        List<POSOutletsTerminals> pOSOutletsTerminalsList;
        try {
            pOSOutletsTerminalsList = posOutletsTerminalsDao.findByOUTLET(plant, outlet);
        }catch (Exception e){
            throw new Exception(e);
        }
        return pOSOutletsTerminalsList;
    }

    public List<POSOutletsTerminals> getByoutletsAndsatus(String plant, String outlet,Short status) throws Exception {
        List<POSOutletsTerminals> pOSOutletsTerminalsList;
        try {
            pOSOutletsTerminalsList = posOutletsTerminalsDao.findByoutletandstatus(plant, status,outlet);
        }catch (Exception e){
            throw new Exception(e);
        }
        return pOSOutletsTerminalsList;
    }

    public String saveterminal(POSOutletsTerminals pOSOutletsTerminals) throws Exception {
        String val = "0";
        try {
            posOutletsTerminalsDao.addPOSOutletsTerminals(pOSOutletsTerminals);
            val = "1";
        }catch (Exception e){
            throw new Exception(e);
        }
        return val;
    }
}
