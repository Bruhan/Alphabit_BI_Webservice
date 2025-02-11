package com.facility.management.usecases.product_request.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApprovePRDTO {
    String projectNo;
    int productRequestHdrId;
    int isApproved;
    String approverRemarks;
    String approverCode;
}
