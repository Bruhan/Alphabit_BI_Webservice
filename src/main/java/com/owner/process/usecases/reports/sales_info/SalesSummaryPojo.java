package com.owner.process.usecases.reports.sales_info;

public interface SalesSummaryPojo {
    String getJournalDate();
    int getId();
    Double getTotalAmount();
    String getCustomerName();
    String getReference();
    String getStatus();
    String getCondition();
}
