package com.facility.management.usecases.product_request.dao;

import com.facility.management.persistence.models.ProductRequestReceiveHDR;
import com.facility.management.usecases.attendance.dto.CalendarRequestDTO;
import com.facility.management.usecases.product_request.dto.*;
import com.facility.management.usecases.product_request.enums.ApprovalStatus;
import com.facility.management.usecases.product_request.enums.RequestStatus;

import java.text.ParseException;
import java.util.List;

public interface ProductRequestDao {
    List<ProductRequestHdrDTO> getProductRequestByCriteria(String plant, String projectNo, String requestorId, RequestStatus requestStatus, ApprovalStatus approvalStatus, String date);

    List<ProductRequestHdrDTO> getApprovedProductRequestByCriteria(String plant, String projectNo, ApprovalStatus approvalStatus);

    ProductRequestHdrDTO getProductRequestById(String plant, int requestId);

    Integer saveProductRequest(String plant, PRHdrRequestDTO productRequestHdrDTO);

    List<ProductRequestHdrDTO> getTodayProductRequest(String plant);

    ProductRequestDetDTO getProductRequestDetByItem(String plant, String item);

    ProductRequestDetDTO getProductRequestDetById(String plant, int detId);

    Integer updateProductRequest(String plant, Integer requestId, ProductRequestHdrDTO productRequestHdrDTO);

    Integer updateProductRequestDET(String plant, ProductRequestDetDTO productRequestDetDTO);

    Integer receiveProductRequest(String plant, ProductRequestReceiveDTO productRequestReceiveDTO);

    List<ProductRequestReceiveDTO> getProductRequestReceiveByCriteria(String plant, String projectNo, String date);

    Integer approveProductRequests(String plant, ApprovePRDTO approvePRDTO);

    List<PRNonReceivedDTO> getProductCurrentStock(String plant, String projectNo);

    ProductRequestReceiveDTO getReceivedProductRequests(String plant, String projectNo, Integer productRequestId);


    List<PRCalendarResponseDTO> hasRequestedProduct(String plant, String projectNo, CalendarRequestDTO calendarRequestDTO) throws ParseException;

    List<PRCalendarResponseDTO> hasReceivedProduct(String plant, String projectNo, CalendarRequestDTO calendarRequestDTO) throws ParseException;
}
