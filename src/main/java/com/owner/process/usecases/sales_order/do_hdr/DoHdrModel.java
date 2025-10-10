package com.owner.process.usecases.sales_order.do_hdr;

import com.owner.process.helpers.common.calc.DateTimeCalc;
import com.owner.process.persistence.models.DoHdr;
import com.owner.process.usecases.sales_order.dao.create_sales_order.SalesOrderMain;
import com.owner.process.usecases.sales_order.dao.create_sales_order.TaxLines;

import java.util.List;

public class DoHdrModel {
    public DoHdr setDoHdrModel(SalesOrderMain salesOrderMain, String plant, String currencyUseQt) {
        DateTimeCalc dateTimeCalc = new DateTimeCalc();
        String[] collectionDateTime = dateTimeCalc
                .dateTimeFormat(salesOrderMain.getCreatedAt()).split(" ");
        String[] collectionUcloDateTime = dateTimeCalc
                .ucloDateTimeFormatTwo(salesOrderMain.getCreatedAt()).split(" ");
        String createdAt = dateTimeCalc.getUcloTodayDateTime();
        String createdBy = "API_Shopify";
        String tnsDate = dateTimeCalc.getTodayDateTime().split(" ")[0];
        String todayDate = dateTimeCalc.getUcloTodayDate();


        DoHdr doHdr = new DoHdr();
        doHdr.setPlant(plant);
        doHdr.setJobNumber(String.valueOf(salesOrderMain.getId()));
        //doHdr.setCollectionDate(collectionUcloDateTime[0]);
        doHdr.setCollectionDate(todayDate);
        //doHdr.setDeliveryDate(collectionUcloDateTime[0]);
        doHdr.setCollectionTime(collectionDateTime[1].substring(0, 5).replaceAll(":", ""));

        doHdr.setCurrencyId(salesOrderMain.getCurrency());
        doHdr.setDoNo(salesOrderMain.getName());

        int taxLineCount = salesOrderMain.getTaxLines().size();
        List<TaxLines> taxLines = salesOrderMain.getTaxLines();
        double taxCollection = 0.0;
        for (int i = 0; i < taxLineCount; i++) {
            taxCollection = taxCollection + taxLines.get(i).getRate();
        }
        doHdr.setOutboundGst((float) (taxCollection * 100));
        if (salesOrderMain.getBillingAddress().getPhone() != null) {
            doHdr.setContactNumber(salesOrderMain.getBillingAddress().getPhone());
        } else {
            doHdr.setContactNumber(salesOrderMain.getCustomer().getPhone());
        }
        //String customerName = defaultAddress.getName();
        doHdr.setCustomerName(salesOrderMain.getBillingAddress().getName());
        doHdr.setAddress(salesOrderMain.getBillingAddress().getAddress1());
        doHdr.setAddressTwo(salesOrderMain.getBillingAddress().getAddress2());
        doHdr.setAddressThree(salesOrderMain.getBillingAddress().getCity());
        doHdr.setCustomerName(salesOrderMain.getBillingAddress().getName());

        //Default value for doheader
        doHdr.setOrderType("Shopify");
        doHdr.setStatus("N");
        doHdr.setPickStatus("N");
        doHdr.setCrAt(createdAt);
        doHdr.setCrBy(createdBy);
        doHdr.setOrderDiscount(0);
        doHdr.setShippingCost(0);
        doHdr.setDeliveryDateFormat(0);
        doHdr.setOrderStatus("Open");
        doHdr.setAdjustment(0);
        doHdr.setItemRates(0);
        doHdr.setProjectId(0);

        //v2
        //doHdr.setDelDate(collectionUcloDateTime[0]);
        doHdr.setDelDate(todayDate);
        doHdr.setCurrencyUseQt(Float.parseFloat(currencyUseQt));
        doHdr.setDiscountType(salesOrderMain.getCurrency());
        doHdr.setOrderDiscountType(salesOrderMain.getCurrency());
        //v3
        doHdr.setDiscount(Float.parseFloat(salesOrderMain.getTotalDiscounts()));
        doHdr.setShippingCost(Float.parseFloat(
                salesOrderMain.getTotalShippingPriceSet().getShopMoney().getAmount()));
        return doHdr;
    }
}
