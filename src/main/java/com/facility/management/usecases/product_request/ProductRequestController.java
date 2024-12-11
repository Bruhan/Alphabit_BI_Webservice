package com.facility.management.usecases.product_request;

import com.facility.management.helpers.common.results.ResultDao;
import com.facility.management.helpers.common.token.ClaimsDao;
import com.facility.management.helpers.common.token.ClaimsSet;
import com.facility.management.usecases.product_request.dto.PRHdrRequestDTO;
import com.facility.management.usecases.product_request.dto.ProductRequestHdrDTO;
import com.facility.management.usecases.product_request.dto.ProductRequestReceiveDTO;
import com.facility.management.usecases.product_request.enums.ApprovalStatus;
import com.facility.management.usecases.product_request.enums.RequestStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("${spring.base.path}")
public class ProductRequestController {

    @Autowired
    ClaimsSet claimsSet;

    @Autowired
    ProductRequestService productRequestService;

    @GetMapping("/product-requests")
    public ResponseEntity<Object> getProductRequestByCriteria(HttpServletRequest request,
                                                              @RequestParam(required = false) String projectNo,
                                                              @RequestParam(required = false) String requestorId,
                                                              @RequestParam(required = false) RequestStatus requestStatus,
                                                              @RequestParam(required = false) ApprovalStatus approvalStatus) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        String plant = claimsDao.getPlt();
//        String plant = "test";

        List<ProductRequestHdrDTO> productRequestHdrDTOList = productRequestService.getProductRequestByCriteria(plant, projectNo, requestorId, requestStatus, approvalStatus);

//        return new ResponseEntity<>(productRequestHdrDTOList, HttpStatus.OK);
        ResultDao resultDao = new ResultDao();
        if(productRequestHdrDTOList == null) {
            resultDao.setResults(productRequestHdrDTOList);
            resultDao.setStatusCode(HttpStatus.NOT_FOUND.value());
            resultDao.setMessage("Not Found");
            return new ResponseEntity<>(resultDao, HttpStatus.NOT_FOUND);
        }
        resultDao.setResults(productRequestHdrDTOList);
        resultDao.setStatusCode(HttpStatus.OK.value());
        resultDao.setMessage("SUCCESS");
        return new ResponseEntity<>(resultDao, HttpStatus.OK);
    }

    @GetMapping("/product-requests/{requestId}")
    public ResponseEntity<Object> getProductRequestById(HttpServletRequest request,
                                                        @PathVariable("requestId") int requestId) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        String plant = claimsDao.getPlt();

        ProductRequestHdrDTO productRequestHdrDTO = productRequestService.getProductRequestById(plant, requestId);
        ResultDao resultDao = new ResultDao();

        if(productRequestHdrDTO == null) {
            resultDao.setResults(productRequestHdrDTO);
            resultDao.setMessage("Not Found");
            return new ResponseEntity<>(resultDao, HttpStatus.NOT_FOUND);
        }
        resultDao.setResults(productRequestHdrDTO);
        resultDao.setMessage("SUCCESS");
        return new ResponseEntity<>(resultDao, HttpStatus.OK);
    }

    @GetMapping("/product-requests/pending/{projectNo}")
    public ResponseEntity<Object> getPendingProductRequests(HttpServletRequest request, @PathVariable("projectNo") String projectNo) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        String plant = claimsDao.getPlt();

        List<ProductRequestHdrDTO> productRequestHdrDTOList = productRequestService.getProductRequestByCriteria(plant, projectNo, null, null, ApprovalStatus.PENDING);

        ResultDao resultDao = new ResultDao();
        resultDao.setResults(productRequestHdrDTOList);
        resultDao.setStatusCode(HttpStatus.OK.value());
        resultDao.setMessage("SUCCESS");
        return new ResponseEntity<>(resultDao, HttpStatus.OK);
    }

    @GetMapping("/product-requests/receive/{projectNo}")
    public ResponseEntity<Object> getProductRequestReceiveByCriteria(HttpServletRequest request, @PathVariable("projectNo") String projectNo) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        String plant = claimsDao.getPlt();

        List<ProductRequestReceiveDTO> productRequestReceiveDTOList = productRequestService.getProductRequestReceiveByCriteria(plant, projectNo);

        ResultDao resultDao = new ResultDao();
        if(productRequestReceiveDTOList == null) {
            resultDao.setResults(productRequestReceiveDTOList);
            resultDao.setStatusCode(HttpStatus.NOT_FOUND.value());
            resultDao.setMessage("Not Found");
            return new ResponseEntity<>(resultDao, HttpStatus.NOT_FOUND);
        }
        resultDao.setResults(productRequestReceiveDTOList);
        resultDao.setStatusCode(HttpStatus.OK.value());
        resultDao.setMessage("SUCCESS");
        return new ResponseEntity<>(resultDao, HttpStatus.OK);
    }

    @PostMapping("product-requests")
    public ResponseEntity<Object> saveProductRequest(HttpServletRequest request, @RequestBody PRHdrRequestDTO productRequestHdrDTO) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        String plant = claimsDao.getPlt();
//        String plant = "test";

        Integer result = productRequestService.saveProductRequest(plant, productRequestHdrDTO);

        ResultDao resultDao = new ResultDao();
        if(result > 0) {
            resultDao.setMessage("SUCCESS");
            resultDao.setStatusCode(HttpStatus.OK.value());
            return new ResponseEntity<>(resultDao, HttpStatus.OK);
        }
        else {
            resultDao.setMessage("FAILED");
            resultDao.setStatusCode(HttpStatus.NOT_IMPLEMENTED.value());
            return new ResponseEntity<>(resultDao, HttpStatus.NOT_IMPLEMENTED);
        }
    }

    @PostMapping("product-requests/receive")
    public ResponseEntity<Object> receiveProductRequest(HttpServletRequest request, @RequestBody ProductRequestReceiveDTO productRequestReceiveDTO) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        String plant = claimsDao.getPlt();
//        String plant = "test";

        Integer result = productRequestService.receiveProductRequest(plant, productRequestReceiveDTO);

        ResultDao resultDao = new ResultDao();
        if(result > 0) {
            resultDao.setMessage("SUCCESS");
            resultDao.setStatusCode(HttpStatus.OK.value());
            return new ResponseEntity<>(resultDao, HttpStatus.OK);
        }
        else {
            resultDao.setMessage("FAILED");
            resultDao.setStatusCode(HttpStatus.NOT_IMPLEMENTED.value());
            return new ResponseEntity<>(resultDao, HttpStatus.NOT_IMPLEMENTED);
        }
    }

    @GetMapping("product-requests/today")
    public ResponseEntity<Object> getTodayProductRequest(HttpServletRequest request) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        String plant = claimsDao.getPlt();
//        String plant = "test";

        List<ProductRequestHdrDTO> productRequestHdrDTOList = productRequestService.getTodayProductRequest(plant);

        ResultDao resultDao = new ResultDao();
        if(productRequestHdrDTOList == null) {
            resultDao.setResults(productRequestHdrDTOList);
            resultDao.setMessage("Not Found");
            return new ResponseEntity<>(resultDao, HttpStatus.NOT_FOUND);
        }
        resultDao.setResults(productRequestHdrDTOList);
        resultDao.setMessage("SUCCESS");
        return new ResponseEntity<>(resultDao, HttpStatus.OK);
    }

    @PutMapping("/product-requests/{requestId}")
    public ResponseEntity<Object> updateProductRequest (HttpServletRequest request,
                                                        @PathVariable("requestId") Integer requestId,
                                                        @RequestParam(required = false) RequestStatus requestStatus,
                                                        @RequestParam(required = false) ApprovalStatus approvalStatus) throws Exception {
                ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
                String plant = claimsDao.getPlt();
//        String plant = "test";

        Integer result = productRequestService.updateProductRequest(plant, requestId, requestStatus, approvalStatus);


        if(result == 0) {
            return new ResponseEntity<>("Product Request Not Found", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>("Product Request updated Successfully", HttpStatus.OK);
    }
}
