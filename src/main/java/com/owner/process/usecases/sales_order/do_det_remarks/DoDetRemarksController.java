package com.owner.process.usecases.sales_order.do_det_remarks;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.RequestBody;
import com.owner.process.persistence.models.DoDetRemarks;
import com.owner.process.helpers.common.token.ClaimsDao;
import com.owner.process.helpers.common.calc.DateTimeCalc;
import com.owner.process.helpers.common.token.ClaimsSet;
import org.springframework.http.HttpStatus;
@RestController
@RequestMapping("${spring.base.path}")
public class DoDetRemarksController {
	@Autowired
	DoDetRemarksService doDetRemarksService;
	@Autowired
	ClaimsSet claimsSet;
	@RequestMapping(value = "/set-doDetRemarks/new", method = RequestMethod.POST)
	public ResponseEntity<?> newdoDetRemarks(HttpServletRequest request,@RequestBody DoDetRemarks val) throws Exception {
		ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
		String plant = claimsDao.getPlt();
		String sub = claimsDao.getSub();
		String empId = claimsDao.getEid();
		DateTimeCalc dateTimeCalc = new DateTimeCalc();
		String createdAt = dateTimeCalc.getUcloTodayDateTime();
		String createdBy = "abdul";
		String status = doDetRemarksService.checkDoDetRemarksPk(val.getDoNo(),val.getDoLineNo(),val.getItem());
		if(status == "1"){
			doDetRemarksService.setDoDetRemarksDetails(val);
		}else{
			throw new Exception("value already set");
		}
	return new ResponseEntity<>("Success", HttpStatus.OK);	}
}
