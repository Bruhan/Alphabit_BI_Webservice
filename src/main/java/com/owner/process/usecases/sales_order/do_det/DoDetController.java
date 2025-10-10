package com.owner.process.usecases.sales_order.do_det;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.RequestBody;
import com.owner.process.persistence.models.DoDet;
import com.owner.process.helpers.common.token.ClaimsDao;
import com.owner.process.helpers.common.calc.DateTimeCalc;
import com.owner.process.helpers.common.token.ClaimsSet;
import org.springframework.http.HttpStatus;
@RestController
@RequestMapping("${spring.base.path}")
public class DoDetController {
	@Autowired
	DoDetService doDetService;
	@Autowired
	ClaimsSet claimsSet;
	@RequestMapping(value = "/set-doDet/new", method = RequestMethod.POST)
	public ResponseEntity<?> newdoDet(HttpServletRequest request,@RequestBody DoDet val) throws Exception {
		DateTimeCalc dateTimeCalc = new DateTimeCalc();
		String createdAt = dateTimeCalc.getUcloTodayDateTime();
		String createdBy = "abdul";
		String status = doDetService.checkDoDetPk(val.getDoNo(),val.getDoLineNo());
		if(status.equals("1")){
			doDetService.setDoDetDetails(val);
		}else{
			throw new Exception("value already set");
		}
	return new ResponseEntity<>("Success", HttpStatus.OK);	}
}
