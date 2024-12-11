package com.facility.management.usecases.chart_of_accounts;

import com.facility.management.helpers.common.calc.DateTimeCalc;
import com.facility.management.helpers.common.token.ClaimsSet;
import com.facility.management.persistence.models.ChartOfAccounts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("${spring.base.path}")
public class ChartOfAccountsController {
	@Autowired
	ChartOfAccountsService chartOfAccountsService;
	@Autowired
	ClaimsSet claimsSet;

	@RequestMapping(value = "/set-chartOfAccounts/new", method = RequestMethod.POST)
	public ResponseEntity<?> newchartOfAccounts(HttpServletRequest request, @RequestBody ChartOfAccounts val) throws Exception {
		DateTimeCalc dateTimeCalc = new DateTimeCalc();
		String createdAt = dateTimeCalc.getUcloTodayDateTime();
		String createdBy = "";
		Boolean status1 = chartOfAccountsService.checkChartOfAccountsPk1(val.getAccountName());
		if (!status1) {
			chartOfAccountsService.setChartOfAccountsDetails(val);
		} else {
			throw new Exception("value already set");
		}
		return new ResponseEntity<>("Success", HttpStatus.OK);
	}
}
