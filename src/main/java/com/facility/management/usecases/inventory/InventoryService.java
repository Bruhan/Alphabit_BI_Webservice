package com.facility.management.usecases.inventory;

import com.facility.management.helpers.configs.LoggerConfig;
import com.facility.management.persistence.models.InvMst;
import com.facility.management.usecases.inventory.dao.InventoryDao;
import com.facility.management.usecases.inventory.pojo.InventoryPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryService {
    @Autowired
    InventoryDao inventoryDao;

    public InvMst findByItemAndLoc(String plant, String itemNo,String loc) throws Exception {
        InvMst invMst = null;
        try {
            invMst = inventoryDao.findByItemAndLoc(plant, itemNo,loc);
        }catch (Exception e){
            throw new Exception(e);
        }
        return invMst;
    }

    public String setInvMst(InvMst val) throws Exception {
        try {
            inventoryDao.save(val);
        } catch (Exception e) {
            LoggerConfig.logger.error(e.getMessage());
            throw new Exception(e.getMessage());
        }
        return "1";
    }

    public String updateInvMst(InvMst val) throws Exception {
        try {
            inventoryDao.update(val);
        } catch (Exception e) {
            LoggerConfig.logger.error(e.getMessage());
            throw new Exception(e.getMessage());
        }
        return "1";
    }

    public List<InventoryPojo> getLocation(String plt) throws Exception {
        List<InventoryPojo>  inventoryPojo;
        try {
            inventoryPojo = inventoryDao.findByLoc(plt);
        }catch (Exception e){
            throw new Exception(e);
        }
        return inventoryPojo;
    }

    public InvMst findByItemAndLocAndBatch(String plant, String itemNo,String loc,String batch) throws Exception {
        InvMst invMst = null;
        try {
            invMst = inventoryDao.findByItemAndLocAndBatch(plant, itemNo,loc,batch);
        }catch (Exception e){
            throw new Exception(e);
        }
        return invMst;
    }

    public List<InvMst> findallByLoc(String plt,String location) throws Exception {
        List<InvMst>  inventoryPojo;
        try {
            inventoryPojo = inventoryDao.findallByLoc(plt,location);
        }catch (Exception e){
            throw new Exception(e);
        }
        return inventoryPojo;
    }
}
