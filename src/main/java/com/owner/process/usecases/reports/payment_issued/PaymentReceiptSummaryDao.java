package com.owner.process.usecases.reports.payment_issued;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PaymentReceiptSummaryDao {
    private int id;
    private String journalDate;
    private Double totalAmount;

    private String accountName;
    private String supplierName;
    private String transactionId;
    private String paidTo;
}
