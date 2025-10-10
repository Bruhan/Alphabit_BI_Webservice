package com.owner.process.usecases.reports.pdc_received_info;

public interface PdcReceivedSummaryPojo {
    int getReceiveId();

    String getReceiveDate();

    String getCustomer();

    String getBankBranch();

    String getChequeNo();

    String getStatus();

    String getChequeDate();

    String getChequeReversalDate();

    Double getChequeAmount();

}
