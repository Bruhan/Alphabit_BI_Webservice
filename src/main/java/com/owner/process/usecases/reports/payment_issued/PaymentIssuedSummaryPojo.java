package com.owner.process.usecases.reports.payment_issued;

public interface PaymentIssuedSummaryPojo {
    int getId();

    String getJournalDate();

    Double getTotalAmount();

    String getStatus();

    String getSupplierName();

    String getAccountName();

    String getTransactionId();

    String getPaidTo();
}
