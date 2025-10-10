package com.owner.process.usecases.reports.accounts_payable;

public interface AccountsPayableSummaryPojo {
    String getSupplier();

    Double getAmountPayable();

    Double getPdcAmount();

    Double getTotalAmount();

}
