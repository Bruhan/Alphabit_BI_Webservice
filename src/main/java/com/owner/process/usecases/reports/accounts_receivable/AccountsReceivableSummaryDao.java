package com.owner.process.usecases.reports.accounts_receivable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountsReceivableSummaryDao {
    private String customer;
    private Double amountPayable;
    private Double pdcAmount;
    private Double totalAmount;
}
