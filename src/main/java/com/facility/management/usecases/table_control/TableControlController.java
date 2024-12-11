package com.facility.management.usecases.table_control;

import com.facility.management.helpers.common.calc.DateTimeCalc;
import com.facility.management.helpers.common.token.ClaimsDao;
import com.facility.management.helpers.common.token.ClaimsSet;
import com.facility.management.persistence.models.TableControl;
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
public class TableControlController {
	@Autowired
	TableControlService tableControlService;
	@Autowired
	ClaimsSet claimsSet;

	@RequestMapping(value = "/set-tableControl/new", method = RequestMethod.POST)
	public ResponseEntity<?> newtableControl(HttpServletRequest request, @RequestBody TableControl val) throws Exception {
		ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
		String plant = claimsDao.getPlt();
		String sub = claimsDao.getSub();
		String empId = claimsDao.getEid();
		DateTimeCalc dateTimeCalc = new DateTimeCalc();
		String createdAt = dateTimeCalc.getUcloTodayDateTime();
		String createdBy = sub;
		String status = tableControlService.checkTableControlPk(val.getPlant(), val.getFunc());
		if (status.equals("1")) {
			tableControlService.setTableControlDetails(val);
		} else {
			throw new Exception("value already set");
		}
		return new ResponseEntity<>("Success", HttpStatus.OK);
	}
}
