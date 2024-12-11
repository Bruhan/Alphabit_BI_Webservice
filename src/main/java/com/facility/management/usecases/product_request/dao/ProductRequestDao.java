package com.facility.management.usecases.product_request.dao;

import com.facility.management.persistence.models.ProductRequestReceiveHDR;
import com.facility.management.usecases.product_request.dto.PRHdrRequestDTO;
import com.facility.management.usecases.product_request.dto.ProductRequestHdrDTO;
import com.facility.management.usecases.product_request.dto.ProductRequestReceiveDTO;
import com.facility.management.usecases.product_request.enums.ApprovalStatus;
import com.facility.management.usecases.product_request.enums.RequestStatus;

import java.util.List;

public interface ProductRequestDao {
    List<ProductRequestHdrDTO> getProductRequestByCriteria(String plant, String projectNo, String requestorId, RequestStatus requestStatus, ApprovalStatus approvalStatus);

    ProductRequestHdrDTO getProductRequestById(String plant, int requestId);

    Integer saveProductRequest(String plant, PRHdrRequestDTO productRequestHdrDTO);

    List<ProductRequestHdrDTO> getTodayProductRequest(String plant);

    Integer updateProductRequest(String plant, Integer requestId, ProductRequestHdrDTO productRequestHdrDTO);

    Integer receiveProductRequest(String plant, ProductRequestReceiveDTO productRequestReceiveDTO);

    List<ProductRequestReceiveDTO> getProductRequestReceiveByCriteria(String plant, String projectNo);
}
