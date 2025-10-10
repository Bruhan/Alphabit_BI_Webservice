package com.owner.process.usecases.reports.purchase_info;

public interface PurchaseSummaryPojo {
    String getJournalDate();
    int getId();
    Double getTotalAmount();
    String getSupplierName();
    String getReference();
    String getStatus();
    String getCondition();
}
