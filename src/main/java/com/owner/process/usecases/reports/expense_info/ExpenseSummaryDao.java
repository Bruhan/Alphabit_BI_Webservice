package com.owner.process.usecases.reports.expense_info;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ExpenseSummaryDao {
    private int id;
    private String journalDate;
    private Double totalAmount;

    private String accountName;
    private String supplierName;
    private String reference;
}
