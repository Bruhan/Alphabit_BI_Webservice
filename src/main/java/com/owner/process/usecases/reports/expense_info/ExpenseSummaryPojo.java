package com.owner.process.usecases.reports.expense_info;

public interface ExpenseSummaryPojo {
    int getId();
    String getJournalDate();
    String getAccountName();

    Double getTotalAmount();
    String getSupplierName();
    String getReference();
}
