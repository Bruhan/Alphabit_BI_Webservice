package com.owner.process.usecases.reports.pdc_issued_info;

public interface PdcIssuedSummaryPojo {
    int getPaymentId();

    String getPaymentDate();

    String getSupplier();

    String getStatus();

    String getChequeNo();

    String getChequeDate();

    String getChequeReversalDate();

    Double getChequeAmount();

}
