package com.owner.process.usecases.sales_order.shopify_item;

import com.owner.process.helpers.common.calc.DateTimeCalc;
import com.owner.process.helpers.common.token.ClaimsDao;
import com.owner.process.helpers.common.token.ClaimsSet;
import com.owner.process.persistence.models.ShopifyItem;
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
public class ShopifyItemController {
	@Autowired
	ShopifyItemService shopifyItemService;
	@Autowired
	ClaimsSet claimsSet;

	@RequestMapping(value = "/set-shopifyItem/new", method = RequestMethod.POST)
	public ResponseEntity<?> newshopifyItem(HttpServletRequest request, @RequestBody ShopifyItem val) throws Exception {
		ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
		String plant = claimsDao.getPlt();
		String sub = claimsDao.getSub();
		String empId = claimsDao.getEid();
		DateTimeCalc dateTimeCalc = new DateTimeCalc();
		String createdAt = dateTimeCalc.getUcloTodayDateTime();
		String createdBy = "abdul";
		String status = shopifyItemService.checkShopifyItemPk(val.getPlant(), val.getSku());
		if (status == "1") {
			shopifyItemService.setShopifyItemDetails(val);
		} else {
			throw new Exception("value already set");
		}
		return new ResponseEntity<>("Success", HttpStatus.OK);
	}
}
