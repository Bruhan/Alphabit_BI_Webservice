package com.facility.management.usecases.customer;

import com.facility.management.helpers.common.results.ResultDao;
import com.facility.management.helpers.common.results.ResultsDao;
import com.facility.management.helpers.common.token.ClaimsDao;
import com.facility.management.helpers.common.token.ClaimsSet;
import com.facility.management.usecases.customer.dto.CustomerDTO;
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
public class CustomerController {

    @Autowired
    ClaimsSet claimsSet;

    @Autowired
    CustomerService customerService;

    @GetMapping("/customers")
    public ResponseEntity<Object> getListOfCustomers(HttpServletRequest request) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        String plant = claimsDao.getPlt();
        List<CustomerDTO> customerDTOList = customerService.getListOfCustomers(plant);

        ResultDao resultDao = new ResultDao();
        resultDao.setResults(customerDTOList);
        resultDao.setStatusCode(HttpStatus.OK.value());
        resultDao.setMessage("SUCCESS");

        return new ResponseEntity<>(resultDao, HttpStatus.OK);

    }

}
