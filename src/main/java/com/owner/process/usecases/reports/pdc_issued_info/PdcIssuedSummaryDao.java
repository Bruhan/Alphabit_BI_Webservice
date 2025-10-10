package com.owner.process.usecases.reports.pdc_issued_info;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PdcIssuedSummaryDao {
    private int paymentId;
    private String paymentDate;
    private String supplier;
    private String bankBranch;
    private String chequeNo;
    private String chequeDate;
    private String chequeReversalDate;
    private String status;
    private Double chequeAmount;
}
