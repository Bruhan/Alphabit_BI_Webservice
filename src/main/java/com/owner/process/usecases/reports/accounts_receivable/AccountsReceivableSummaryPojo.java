package com.owner.process.usecases.reports.accounts_receivable;

public interface AccountsReceivableSummaryPojo {
    String getCustomer();

    Double getAmountPayable();

    Double getPdcAmount();

    Double getTotalAmount();

}
