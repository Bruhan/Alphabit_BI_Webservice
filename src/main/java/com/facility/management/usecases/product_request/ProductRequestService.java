package com.facility.management.usecases.product_request;

import com.facility.management.helpers.common.calc.DateTimeCalc;
import com.facility.management.persistence.models.ProjectStockRequestHDR;
import com.facility.management.usecases.product_request.dao.ProductRequestDao;
import com.facility.management.usecases.product_request.dto.PRHdrRequestDTO;
import com.facility.management.usecases.product_request.dto.ProductRequestHdrDTO;
import com.facility.management.usecases.product_request.dto.ProductRequestReceiveDTO;
import com.facility.management.usecases.product_request.enums.ApprovalStatus;
import com.facility.management.usecases.product_request.enums.RequestStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ProductRequestService {

    @Autowired
    ProductRequestDao productRequestDao;

    public List<ProductRequestHdrDTO> getProductRequestByCriteria(String plant, String projectNo, String requestorId, RequestStatus requestStatus, ApprovalStatus approvalStatus) {
        List<ProductRequestHdrDTO> productRequestHdrDTOList = null;
        try {
//            date = (date == null) ? "" : date;
//            startDate = (startDate == null) ? "" : startDate;
//            endDate = (endDate == null) ? "" : endDate;

            productRequestHdrDTOList = productRequestDao.getProductRequestByCriteria(plant, projectNo, requestorId, requestStatus, approvalStatus);

            return productRequestHdrDTOList;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public ProductRequestHdrDTO getProductRequestById(String plant, int requestId) {
        ProductRequestHdrDTO productRequestHdrDTO = null;
        try {
            productRequestHdrDTO = productRequestDao.getProductRequestById(plant, requestId);
            return productRequestHdrDTO;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public Integer saveProductRequest(String plant, PRHdrRequestDTO productRequestHdrDTO) {
        Integer result = 0;
        try {
            String DATE_PATTERN = "^(0[1-9]|[12][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\\d{4})$";
            Pattern pattern = Pattern.compile(DATE_PATTERN);

            Matcher requestedDateMatcher = pattern.matcher(productRequestHdrDTO.getRequestedDate());

//            Matcher approvalDateMatcher = pattern.matcher(productRequestHdrDTO.getApprovalDate());

            if(requestedDateMatcher.matches()) {
                DateTimeCalc dateTimeCalc = new DateTimeCalc();
                productRequestHdrDTO.setRequestedDate(dateTimeCalc.convertToDMYDate(productRequestHdrDTO.getRequestedDate(), "dd/MM/yyyy"));
            }

//            if(approvalDateMatcher.matches()) {
//                DateTimeCalc dateTimeCalc = new DateTimeCalc();
//                productRequestHdrDTO.setApprovalDate(dateTimeCalc.convertToDMYDate(productRequestHdrDTO.getApprovalDate(), "dd/MM/yyyy"));
//            }

            result = productRequestDao.saveProductRequest(plant, productRequestHdrDTO);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        return result;
    }

    public List<ProductRequestHdrDTO> getTodayProductRequest(String plant) {
        List<ProductRequestHdrDTO> productRequestHdrDTOList = null;
        try {
            productRequestHdrDTOList = productRequestDao.getTodayProductRequest(plant);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        return productRequestHdrDTOList;
    }

    public Integer updateProductRequest(String plant, Integer requestId, RequestStatus requestStatus, ApprovalStatus approvalStatus) {
        Integer result = null;
        try {
            ProductRequestHdrDTO productRequestHdrDTO = productRequestDao.getProductRequestById(plant, requestId);

            if(requestStatus != null) {
                productRequestHdrDTO.setRequestStatus(requestStatus);
            }

            if(approvalStatus != null) {
                productRequestHdrDTO.setApprovalStatus(approvalStatus);
            }

            result = productRequestDao.updateProductRequest(plant, requestId, productRequestHdrDTO);

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        return result;
    }

    public Integer receiveProductRequest(String plant, ProductRequestReceiveDTO productRequestReceiveDTO) {
        Integer result = 0;
        try {

            result = productRequestDao.receiveProductRequest(plant, productRequestReceiveDTO);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        return result;
    }

    public List<ProductRequestReceiveDTO> getProductRequestReceiveByCriteria(String plant, String projectNo) {
        List<ProductRequestReceiveDTO> productRequestReceiveDTOList = null;
        try {
//            date = (date == null) ? "" : date;
//            startDate = (startDate == null) ? "" : startDate;
//            endDate = (endDate == null) ? "" : endDate;

            productRequestReceiveDTOList = productRequestDao.getProductRequestReceiveByCriteria(plant, projectNo);

            return productRequestReceiveDTOList;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
