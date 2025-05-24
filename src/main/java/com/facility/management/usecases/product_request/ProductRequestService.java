package com.facility.management.usecases.product_request;

import com.facility.management.helpers.common.calc.DateTimeCalc;
import com.facility.management.persistence.models.ProjectStockRequestHDR;
import com.facility.management.usecases.activity_log.ActivityLogModel;
import com.facility.management.usecases.activity_log.ActivityLogService;
import com.facility.management.usecases.attendance.dto.CalendarRequestDTO;
import com.facility.management.usecases.product_request.dao.ProductRequestDao;
import com.facility.management.usecases.product_request.dto.*;
import com.facility.management.usecases.product_request.enums.ApprovalStatus;
import com.facility.management.usecases.product_request.enums.LNStatus;
import com.facility.management.usecases.product_request.enums.RequestStatus;
import com.facility.management.usecases.project.ProjectService;
import com.facility.management.usecases.wastage.dto.WastageCalendarResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ProductRequestService {

    @Autowired
    ProductRequestDao productRequestDao;

    @Autowired
    ActivityLogService activityLogService;

    @Autowired
    ProjectService projectService;

    public List<ProductRequestHdrDTO> getProductRequestByCriteria(String plant, String projectNo, String requestorId, RequestStatus requestStatus, ApprovalStatus approvalStatus, String date) {
        List<ProductRequestHdrDTO> productRequestHdrDTOList = null;
        try {
//            date = (date == null) ? "" : date;
//            startDate = (startDate == null) ? "" : startDate;
//            endDate = (endDate == null) ? "" : endDate;

            productRequestHdrDTOList = productRequestDao.getProductRequestByCriteria(plant, projectNo, requestorId, requestStatus, approvalStatus, date);

            return productRequestHdrDTOList;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }


    public List<ProductRequestHdrDTO> getApprovedProductRequestByCriteria(String plant, String projectNo, ApprovalStatus approvalStatus) {
        List<ProductRequestHdrDTO> productRequestHdrDTOList = null;
        try {

            productRequestHdrDTOList = productRequestDao.getApprovedProductRequestByCriteria(plant, projectNo, approvalStatus);

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

            String executiveNo = projectService.getExecutiveByProjectNo(plant, productRequestHdrDTO.getProjectNo());
            productRequestHdrDTO.setApproverCode(executiveNo);

//            if(approvalDateMatcher.matches()) {
//                DateTimeCalc dateTimeCalc = new DateTimeCalc();
//                productRequestHdrDTO.setApprovalDate(dateTimeCalc.convertToDMYDate(productRequestHdrDTO.getApprovalDate(), "dd/MM/yyyy"));
//            }

            DateTimeCalc dateTimeCalc = new DateTimeCalc();
            ActivityLogModel activityLogModel = new ActivityLogModel();
            activityLogService.setActivityLogDetails(activityLogModel.setActivityLogModel(
                    plant, "SAVE_PRODUCT_REQUEST", "", "", "", 0.0,
                    productRequestHdrDTO.getRequesterId(), dateTimeCalc.getTodayDMYDate(), dateTimeCalc.getTodayDMYDate(), productRequestHdrDTO.getRequesterId(),
                    "CREATED", ""));
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

            DateTimeCalc dateTimeCalc = new DateTimeCalc();
            ActivityLogModel activityLogModel = new ActivityLogModel();
            activityLogService.setActivityLogDetails(activityLogModel.setActivityLogModel(
                    plant, "UPDATE_PRODUCT_REQUEST", "", "", "", 0.0,
                    productRequestHdrDTO.getRequesterId(), dateTimeCalc.getTodayDMYDate(), dateTimeCalc.getTodayDMYDate(), productRequestHdrDTO.getRequesterId(),
                    "CREATED", ""));
            result = productRequestDao.updateProductRequest(plant, requestId, productRequestHdrDTO);

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        return result;
    }

    public HashMap<String, Integer> receiveProductRequest(String plant, ProductRequestReceiveDTO productRequestReceiveDTO) {
        HashMap<String, Integer> result = new HashMap<>();
        try {

            DateTimeCalc dateTimeCalc = new DateTimeCalc();
            ActivityLogModel activityLogModel = new ActivityLogModel();

            ProductRequestHdrDTO productRequestHdrDTO = productRequestDao.getProductRequestById(plant, productRequestReceiveDTO.getId());

            boolean isPartiallyReceived = false;


            System.out.println(productRequestHdrDTO);

            for(ProductRequestReceiveProductDTO productRequestReceiveProductDTO: productRequestReceiveDTO.getProductRequestReceiveProductDTOList()) {
                ProductRequestDetDTO productRequestDetDTO = productRequestDao.getProductRequestDetById(plant, productRequestReceiveProductDTO.getId());

                if(!isPartiallyReceived) {
                    isPartiallyReceived = productRequestReceiveProductDTO.getReceivedQty() != productRequestReceiveProductDTO.getQuantity();
                }

                if(productRequestReceiveProductDTO.getProcessedQty() == productRequestReceiveProductDTO.getQuantity()) {
                    productRequestDetDTO.setLnStatus(LNStatus.C);
                } else if (productRequestReceiveProductDTO.getProcessedQty() == 0) {
                    productRequestDetDTO.setLnStatus(LNStatus.N);
                } else {
                    productRequestDetDTO.setLnStatus(LNStatus.O);
                }

                productRequestDetDTO.setReceivedQty(productRequestReceiveProductDTO.getReceivedQty());
                productRequestDetDTO.setNonReceivedQty(productRequestReceiveProductDTO.getNonReceivedQty());
                productRequestDetDTO.setProcessedQty(productRequestReceiveProductDTO.getProcessedQty());
                productRequestDetDTO.setBalanceQty(productRequestReceiveProductDTO.getBalanceQty());

                Integer updateProductRequestDet = productRequestDao.updateProductRequestDET(plant, productRequestDetDTO);
                result.put("updateProductRequestDET - " + productRequestReceiveProductDTO.getId(), updateProductRequestDet);
            }

            if(isPartiallyReceived) {
                productRequestHdrDTO.setRequestStatus(RequestStatus.PARTIALLY_RECEIVED);
            } else {
                productRequestHdrDTO.setRequestStatus(RequestStatus.RECEIVED);
            }

            Integer updateProductRequestHdr = productRequestDao.updateProductRequest(plant, productRequestReceiveDTO.getId(), productRequestHdrDTO);
            result.put("updateProductRequestHdr", updateProductRequestHdr);


            activityLogService.setActivityLogDetails(activityLogModel.setActivityLogModel(
                    plant, "RECEIVE_PRODUCT_REQUEST", "", "", "", 0.0,
                    productRequestReceiveDTO.getReceiverId(), dateTimeCalc.getTodayDMYDate(), dateTimeCalc.getTodayDMYDate(), productRequestReceiveDTO.getReceiverId(),
                    "CREATED", ""));


            Integer receiveProductRequest = productRequestDao.receiveProductRequest(plant, productRequestReceiveDTO);
            result.put("receiveProductRequest", receiveProductRequest);

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        return result;
    }

    public List<ProductRequestReceiveDTO> getProductRequestReceiveByCriteria(String plant, String projectNo, String date) {
        List<ProductRequestReceiveDTO> productRequestReceiveDTOList = null;
        try {
//            date = (date == null) ? "" : date;
//            startDate = (startDate == null) ? "" : startDate;
//            endDate = (endDate == null) ? "" : endDate;

            productRequestReceiveDTOList = productRequestDao.getProductRequestReceiveByCriteria(plant, projectNo, date);

            return productRequestReceiveDTOList;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public Integer approveProductRequests(String plant, ApprovePRDTO approvePRDTO) {
        Integer result = 0;
        try {
            DateTimeCalc dateTimeCalc = new DateTimeCalc();
            ActivityLogModel activityLogModel = new ActivityLogModel();
            activityLogService.setActivityLogDetails(activityLogModel.setActivityLogModel(
                    plant, "APPROVE_PRODUCT_REQUEST", "", "", "", 0.0,
                    approvePRDTO.getApproverCode(), dateTimeCalc.getTodayDMYDate(), dateTimeCalc.getTodayDMYDate(), approvePRDTO.getApproverCode(),
                    "CREATED", ""));
            result = productRequestDao.approveProductRequests(plant, approvePRDTO);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        return result;
    }

    public List<PRNonReceivedDTO> getProductCurrentStock(String plant, String projectNo) {
        List<PRNonReceivedDTO> prNonReceivedDTOList = null;
        try {
            prNonReceivedDTOList = productRequestDao.getProductCurrentStock(plant, projectNo);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        return prNonReceivedDTOList;
    }

    public ProductRequestReceiveDTO getReceivedProductRequests(String plant, String projectNo, Integer productRequestId) {
        ProductRequestReceiveDTO productRequestReceiveDTO = null;
        try {
            productRequestReceiveDTO = productRequestDao.getReceivedProductRequests(plant, projectNo, productRequestId);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        return productRequestReceiveDTO;
    }

    public List<PRCalendarResponseDTO> hasRequestedProduct(String plant, String projectNo, CalendarRequestDTO calendarRequestDTO) {
        List<PRCalendarResponseDTO> result = null;
        try {
            result = productRequestDao.hasRequestedProduct(plant, projectNo, calendarRequestDTO);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

        return result;
    }

    public List<PRCalendarResponseDTO> hasReceivedProduct(String plant, String projectNo, CalendarRequestDTO calendarRequestDTO) {
        List<PRCalendarResponseDTO> result = null;
        try {
            result = productRequestDao.hasReceivedProduct(plant, projectNo, calendarRequestDTO);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

        return result;
    }
}
