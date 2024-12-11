package com.facility.management.usecases.inventory;

import com.facility.management.persistence.models.InvMst;
import com.facility.management.usecases.products.productDao.ItemMstDao;

public class InventoryModel {

    public InvMst setInventoryModel(String plant, String crAt, String crBy, ItemMstDao itemMstDao, String location){
        InvMst invMst = new InvMst();
        invMst.setId(0);
        invMst.setPlant(plant);
        invMst.setQuantity(itemMstDao.getMaximumStackQuantity());
        invMst.setItem(itemMstDao.getItem());
        invMst.setLocation(location);
        invMst.setQuantityAllocation(0.00F);
        invMst.setCrAt(crAt);
        invMst.setCrBy(crBy);
        invMst.setUserFieldFour("");

        return invMst;
    }
}
