package com.facility.management.usecases.inventory.dao;

import com.facility.management.persistence.models.InvMst;
import com.facility.management.usecases.inventory.pojo.InventoryPojo;

import java.util.List;

public interface InventoryDao {

    InvMst findByIdAndItemAndLocationAndUserFieldFour(String plant, Integer pk0, String pk1, String pk2, String pk3);

    double getTQtyByItemLocBatch(String plant, String pk0,String pk1,String pk2);

    List<InvMst> findByItemAndLocationAndUserFieldFour(String plant, String pk0, String pk1, String pk2);

    List<InvMst>  getByItemLocBatch(String plant, String pk0,String pk1,String pk2);

    InvMst findByItemAndLoc(String plant, String item,String loc);

    List<InventoryPojo> findByLoc(String plt);

    List<InvMst> findallByLoc(String plant, String loc);

    InvMst findByItem(String plant, String item);

    InvMst findByItemAndLocAndBatch(String plant, String item,String loc,String batch);

    void save(InvMst invMst);

    void update(InvMst invMst);

}
