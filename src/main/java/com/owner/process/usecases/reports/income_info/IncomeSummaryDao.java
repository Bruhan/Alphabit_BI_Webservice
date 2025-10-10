package com.owner.process.usecases.reports.income_info;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class IncomeSummaryDao {
    private String journalDate;
    private int id;
    private Double totalAmount;

    private String accountName;
    private String customerName;
    private String reference;
}
