package com.owner.process.usecases.reports.pdc_received_info;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PdcReceivedSummaryDao {
    private int receiveId;
    private String receiveDate;
    private String customer;
    private String bankBranch;
    private String status;
    private String chequeNo;
    private String chequeDate;
    private String chequeReversalDate;
    private Double chequeAmount;
}
