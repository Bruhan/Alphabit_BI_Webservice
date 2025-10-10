package com.owner.process.usecases.sales_order.shipment_history;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.RequestBody;
import com.owner.process.persistence.models.ShipHis;
import com.owner.process.helpers.common.token.ClaimsDao;
import com.owner.process.helpers.common.calc.DateTimeCalc;
import com.owner.process.helpers.common.token.ClaimsSet;
import org.springframework.http.HttpStatus;
@RestController
@RequestMapping("${spring.base.path}")
public class ShipHisController {
	@Autowired
	ShipHisService shipHisService;
	@Autowired
	ClaimsSet claimsSet;
	@RequestMapping(value = "/set-shipHis/new", method = RequestMethod.POST)
	public ResponseEntity<?> newshipHis(HttpServletRequest request,@RequestBody ShipHis val) throws Exception {
		DateTimeCalc dateTimeCalc = new DateTimeCalc();
		String createdAt = dateTimeCalc.getUcloTodayDateTime();
		String createdBy = "abdul";
		Boolean status2 = shipHisService.checkShipHisPk2(val.getDoNo());
		Boolean status3 = shipHisService.checkShipHisPk3(val.getDoNo(),val.getItem());
		Boolean status4 = shipHisService.checkShipHisPk4(val.getDoNo(),val.getDoLineNo(),val.getItem());
		if(status2){
			shipHisService.setShipHisDetails(val);
		}else{
			throw new Exception("value already set");
		}
	return new ResponseEntity<>("Success", HttpStatus.OK);	}
}
