package com.owner.process.usecases.sales_order.customer_master;

import com.owner.process.helpers.common.calc.DateTimeCalc;
import com.owner.process.helpers.common.token.ClaimsSet;
import com.owner.process.persistence.models.CustomerMst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("${spring.base.path}")
public class CustomerMstController {
	@Autowired
	CustomerMstService customerMstService;
	@Autowired
	ClaimsSet claimsSet;

	@RequestMapping(value = "/set-customerMst/new", method = RequestMethod.POST)
	public ResponseEntity<?> newcustomerMst(HttpServletRequest request, @RequestBody CustomerMst val) throws Exception {
		DateTimeCalc dateTimeCalc = new DateTimeCalc();
		String createdAt = dateTimeCalc.getUcloTodayDateTime();
		String createdBy = "abdul";
//		Boolean status1 = customerMstService.checkCustomerMstPk1(val.getCustomerNo());
//		Boolean status2 = customerMstService.checkCustomerMstPk2(val.getCustomerName());
//		Boolean status3 = customerMstService.checkCustomerMstPk3(val.getWorkPhone());
//		Boolean status4 = customerMstService.checkCustomerMstPk4(val.getEmail());
		if (true) {
			customerMstService.setCustomerMstDetails(val);
		} else {
			throw new Exception("value already set");
		}
		return new ResponseEntity<>("Success", HttpStatus.OK);
	}

	@RequestMapping(value = "/customerMst/all", method = RequestMethod.GET)
	public ResponseEntity<?> getAllCustomer(HttpServletRequest request) throws Exception {
		List<CustomerMst> val;
		if (true) {
			val  = customerMstService.getAllCustomer();
		} else {
			throw new Exception("Error in getting customer");
		}
		return new ResponseEntity<>(val, HttpStatus.OK);
	}


}
