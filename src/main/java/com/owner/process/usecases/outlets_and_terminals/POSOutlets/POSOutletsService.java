package com.owner.process.usecases.outlets_and_terminals.POSOutlets;

import com.owner.process.persistence.models.POSOutlets;
import com.owner.process.usecases.outlets_and_terminals.POSOutlets.dao.POSOutletsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class POSOutletsService {

    @Autowired
    POSOutletsDao posOutletsDao;

    public POSOutlets getByoutlets(String plant, String outlet) throws Exception {
        POSOutlets pOSOutlets;
        try {
            pOSOutlets = posOutletsDao.findByOUTLET(plant, outlet);
        }catch (Exception e){
            throw new Exception(e);
        }
        return pOSOutlets;
    }

    public List<POSOutlets> getall(String plant) throws Exception {
        List<POSOutlets> pOSOutlets;
        try {
            pOSOutlets = posOutletsDao.findAll(plant);
        }catch (Exception e){
            throw new Exception(e);
        }
        return pOSOutlets;
    }
}
