package com.owner.process.usecases.Supplier;

import com.owner.process.helpers.common.results.ResultsDTO;
import com.owner.process.helpers.common.token.ClaimsDao;
import com.owner.process.helpers.common.token.ClaimsSet;
import com.owner.process.persistence.models.VendMst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
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
        ResultsDTO result = new ResultsDTO();
        result.setResults(supplierlist);
        result.setPageNumber(1);
        result.setPageSize(1);
        return  new ResponseEntity<>(result, HttpStatus.OK);
    }
}
