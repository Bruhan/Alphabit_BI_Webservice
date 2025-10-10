package com.owner.process.usecases.reports.payment_issued;

public interface PaymentReceiptSummaryPojo {
    int getId();
    String getJournalDate();
    Double getTotalAmount();

    String getSupplierName();
    String getAccountName();

    String getTransactionId();
    String getPaidTo();
}
