package com.owner.process.usecases.sales_order.do_hdr;

import com.owner.process.helpers.common.calc.DateTimeCalc;
import com.owner.process.helpers.common.token.ClaimsSet;
import com.owner.process.persistence.models.DoHdr;
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
public class DoHdrController {
	@Autowired
	DoHdrService doHdrService;
	@Autowired
	ClaimsSet claimsSet;
	@RequestMapping(value = "/set-doHdr/new", method = RequestMethod.POST)
	public ResponseEntity<?> newdoHdr(HttpServletRequest request,@RequestBody DoHdr val) throws Exception {
		DateTimeCalc dateTimeCalc = new DateTimeCalc();
		String createdAt = dateTimeCalc.getUcloTodayDateTime();
		String createdBy = "abdul";
		String status = doHdrService.checkDoHdrPk(val.getDoNo());
		if(status.equals("1")){
			doHdrService.setDoHdrDetails(val);
		}else{
			throw new Exception("value already set");
		}
	return new ResponseEntity<>("Success", HttpStatus.OK);	}
}
