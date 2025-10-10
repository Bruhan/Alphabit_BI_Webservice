package com.owner.process.usecases.reports.sales_info;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SalesSummaryDao {
    private String journalDate;
    private int id;
    private Double totalAmount;
    private String customerName;
    private String reference;
    private String status;
    private String condition;
}
