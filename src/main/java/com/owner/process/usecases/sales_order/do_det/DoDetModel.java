package com.owner.process.usecases.sales_order.do_det;

import com.owner.process.persistence.models.DoDet;
import com.owner.process.usecases.sales_order.dao.create_sales_order.LineItems;
import com.owner.process.usecases.sales_order.dao.create_sales_order.SalesOrderMain;

public class DoDetModel {
    public DoDet setDoDetModel(SalesOrderMain salesOrderMain, String plant, String trnDate
            , String currencyUseQt, int i, String UOM, String createdAt, String createdBy) {
        LineItems lineItem = salesOrderMain.getLineItems().get(i);
        DoDet doDet = new DoDet();
        doDet.setPlant(plant);
        doDet.setTransactionDate(trnDate);
        doDet.setCurrencySequence(Float.parseFloat(currencyUseQt));
        doDet.setDoNo(salesOrderMain.getName());
        doDet.setUserFieldThree(salesOrderMain.getBillingAddress().getName());

        int lineNumber = i + 1;
        doDet.setDoLineNo(lineNumber);
        doDet.setUnitMo(String.valueOf(lineItem.getGrams()));
        doDet.setItemDescription(lineItem.getName());
        doDet.setUserFieldOne(lineItem.getName());
        doDet.setUnitPrice(Float.parseFloat(lineItem.getPrice()));
        doDet.setQuantityOr(lineItem.getQuantity());
        doDet.setItem(lineItem.getSku());
        //Default value for dodet
        doDet.setEstimateLineNo(0);
        doDet.setPickStatus("N");
        doDet.setLineStatus("N");
        doDet.setQuantityIs(0.0F);
        doDet.setQuantityPick(0.0F);
        doDet.setUnitMo(UOM);
        doDet.setCrAt(createdAt);
        doDet.setCrBy(createdBy);
        // v2
        doDet.setAccountName("Local sales - retail");
        // v4
        doDet.setTaxType("STANDARD RATED [0.0%]");
        return doDet;
    }
}
