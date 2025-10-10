package com.owner.process.usecases.sales_order.item_mst;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.RequestBody;
import com.owner.process.persistence.models.ItemMst;
import com.owner.process.helpers.common.token.ClaimsDao;
import com.owner.process.helpers.common.calc.DateTimeCalc;
import com.owner.process.helpers.common.token.ClaimsSet;
import org.springframework.http.HttpStatus;
@RestController
@RequestMapping("${spring.base.path}")
public class ItemMstController {
	@Autowired
	ItemMstService itemMstService;
	@Autowired
	ClaimsSet claimsSet;
	@RequestMapping(value = "/set-itemMst/new", method = RequestMethod.POST)
	public ResponseEntity<?> newitemMst(HttpServletRequest request,@RequestBody ItemMst val) throws Exception {
		DateTimeCalc dateTimeCalc = new DateTimeCalc();
		String createdAt = dateTimeCalc.getUcloTodayDateTime();
		String createdBy = "abdul";
		String status = itemMstService.checkItemMstPk(val.getItem());
		if(status.equals("1")){
			itemMstService.setItemMstDetails(val);
		}else{
			throw new Exception("value already set");
		}
	return new ResponseEntity<>("Success", HttpStatus.OK);	}
}
