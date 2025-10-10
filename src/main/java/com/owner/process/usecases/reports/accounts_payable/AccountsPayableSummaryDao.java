package com.owner.process.usecases.reports.accounts_payable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountsPayableSummaryDao {
    private String supplier;
    private Double amountPayable;
    private Double pdcAmount;
    private Double totalAmount;
}
