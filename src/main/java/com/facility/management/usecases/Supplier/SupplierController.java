package com.facility.management.usecases.Supplier;

import com.facility.management.helpers.common.results.ResultsDao;
import com.facility.management.helpers.common.token.ClaimsDao;
import com.facility.management.helpers.common.token.ClaimsSet;
import com.facility.management.persistence.models.ToolBoxMeeting;
import com.facility.management.persistence.models.ToolboxChecklistDet;
import com.facility.management.persistence.models.ToolboxChecklistMst;
import com.facility.management.persistence.models.VendMst;
import com.facility.management.usecases.toolbox.pojo.ToolboxCheckListHdrDetPojo;
import com.facility.management.usecases.toolbox.pojo.ToolboxMeetingPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("${spring.base.path}")
public class SupplierController {

    @Autowired
    VendorService vendorService;

    @Autowired
    ClaimsSet claimsSet;

    @GetMapping("/supplier/getall")
    public ResponseEntity<?> getallsupplier(HttpServletRequest request) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        List<VendMst> supplierlist = vendorService.getallsupplier();
        ResultsDao result = new ResultsDao();
        result.setResults(supplierlist);
        result.setPageNumber(1);
        result.setPageSize(1);
        return  new ResponseEntity<>(result, HttpStatus.OK);
    }
}
