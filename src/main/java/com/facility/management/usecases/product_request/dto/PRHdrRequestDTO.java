package com.facility.management.usecases.product_request.dto;

import com.facility.management.usecases.product_request.enums.ApprovalStatus;
import com.facility.management.usecases.product_request.enums.RequestStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PRHdrRequestDTO {
    private String plant;
    private int projectId;
    private List<PRDetRequestDTO> productRequestDetDTOList;
    private String projectNo;
    private String requestedDate;
    private RequestStatus requestStatus;
    private String requesterId;
    private String requesterRemarks;
    private String approverCode;
    private String approvalStatus;
}
