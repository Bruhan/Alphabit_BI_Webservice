package com.owner.process.usecases.reports.income_info;

public interface IncomeSummaryPojo {
    String getJournalDate();
    int getId();
    String getAccountName();
    Double getTotalAmount();
    String getCustomerName();
    String getReference();
}
