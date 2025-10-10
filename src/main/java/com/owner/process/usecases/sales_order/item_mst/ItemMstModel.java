package com.owner.process.usecases.sales_order.item_mst;

import com.owner.process.persistence.models.ItemMst;
import com.owner.process.usecases.sales_order.dao.create_sales_order.LineItems;
import com.owner.process.usecases.sales_order.dao.create_sales_order.SalesOrderMain;

public class ItemMstModel {

    public ItemMst setItemMstModel(String plant, SalesOrderMain salesOrderMain, int i, String UOM,
                                   String createdAt, String createdBy) {
        LineItems lineItem = salesOrderMain.getLineItems().get(i);
        ItemMst itemMst = new ItemMst();
        itemMst.setPlant(plant);
        itemMst.setItemDescription(lineItem.getName());
        itemMst.setUnitPrice(Float.parseFloat(lineItem.getPrice()));
        itemMst.setItem(lineItem.getSku());
        //repeated
        if (lineItem.getVariantInventoryManagement() == null)
            itemMst.setNonStackFlag("Y");
        else
            itemMst.setNonStackFlag("N");
        //Default value for itemmst
        itemMst.setCost(0.0F);
        itemMst.setDiscount(0.0F);
        itemMst.setItemType("NOTYPE");
        itemMst.setProductBrandId("NOBRAND");
        itemMst.setStackUom(UOM);
        itemMst.setProductClassId("NOCLASSIFICATION");
        itemMst.setItemType("NOTYPE");
        itemMst.setIsActive("Y");
        itemMst.setMinsPrice(0.0F);
        itemMst.setProductGst(0.0F);
        //itemMst.setDiscount(0);
        itemMst.setNetWeight(0.0F);
        itemMst.setGrossWeight(0.0F);
        itemMst.setCrAt(createdAt);
        itemMst.setCrBy(createdBy);
        itemMst.setUserFieldOne("N");
        itemMst.setStackQuantity(0.0F);
        itemMst.setMaximumStackQuantity(0.0F);
        //v2
        itemMst.setIsBasicUom(1);
        itemMst.setPurchaseUom(UOM);
        itemMst.setSalesUom(UOM);
        itemMst.setInventoryUom(UOM);
        return itemMst;
    }
}
