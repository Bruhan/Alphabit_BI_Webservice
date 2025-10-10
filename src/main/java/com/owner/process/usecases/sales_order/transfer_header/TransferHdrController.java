package com.owner.process.usecases.sales_order.transfer_header;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.RequestBody;
import com.owner.process.persistence.models.DoTransferHdr;
import com.owner.process.helpers.common.token.ClaimsDao;
import com.owner.process.helpers.common.calc.DateTimeCalc;
import com.owner.process.helpers.common.token.ClaimsSet;
import org.springframework.http.HttpStatus;
@RestController
@RequestMapping("${spring.base.path}")
public class TransferHdrController {
	@Autowired
	TransferHdrService transferHdrService;
	@Autowired
	ClaimsSet claimsSet;
	@RequestMapping(value = "/set-transferHdr/new", method = RequestMethod.POST)
	public ResponseEntity<?> newtransferHdr(HttpServletRequest request,@RequestBody DoTransferHdr val) throws Exception {
		ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
		String plant = claimsDao.getPlt();
		String sub = claimsDao.getSub();
		String empId = claimsDao.getEid();
		DateTimeCalc dateTimeCalc = new DateTimeCalc();
		String createdAt = dateTimeCalc.getUcloTodayDateTime();
		String createdBy = "abdul";
		String valCheck = transferHdrService.checkDoTransferHdrPk(val.getDoNo());
		if(valCheck == "1"){
			transferHdrService.setDoTransferHdrDetails(val);
		}else{
			throw new Exception("value already set");
		}
	return new ResponseEntity<>("Success", HttpStatus.OK);	}
}
