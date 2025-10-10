package com.owner.process.usecases.reports.purchase_info;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PurchaseSummaryDao {
    private String journalDate;
    private int id;
    private Double totalAmount;
    private String supplierName;
    private String reference;
    private String status;
    private String condition;
}
