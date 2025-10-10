package com.owner.process.usecases.sales_order.inventory_master;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.RequestBody;
import com.owner.process.persistence.models.InvMst;
import com.owner.process.helpers.common.token.ClaimsDao;
import com.owner.process.helpers.common.calc.DateTimeCalc;
import com.owner.process.helpers.common.token.ClaimsSet;
import org.springframework.http.HttpStatus;
@RestController
@RequestMapping("${spring.base.path}")
public class InvMstController {
	@Autowired
	InvMstService invMstService;
	@Autowired
	ClaimsSet claimsSet;
	@RequestMapping(value = "/set-invMst/new", method = RequestMethod.POST)
	public ResponseEntity<?> newinvMst(HttpServletRequest request,@RequestBody InvMst val) throws Exception {
		DateTimeCalc dateTimeCalc = new DateTimeCalc();
		String createdAt = dateTimeCalc.getUcloTodayDateTime();
		String createdBy = "abdul";
		Boolean status1 = invMstService.checkInvMstPk1(val.getId(),val.getItem(),val.getLocation(),val.getUserFieldFour());
		if(status1){
			invMstService.setInvMstDetails(val);
		}else{
			throw new Exception("value already set");
		}
	return new ResponseEntity<>("Success", HttpStatus.OK);	}
}
