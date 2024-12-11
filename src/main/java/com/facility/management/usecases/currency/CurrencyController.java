package com.facility.management.usecases.currency;

import com.facility.management.helpers.common.calc.DateTimeCalc;
import com.facility.management.helpers.common.token.ClaimsDao;
import com.facility.management.helpers.common.token.ClaimsSet;
import com.facility.management.persistence.models.CurrencyMst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("${spring.base.path}")
public class CurrencyController {
    @Autowired
    CurrencyService currencyService;
    @Autowired
    ClaimsSet claimsSet;

    @GetMapping("/currency-info")
    public ResponseEntity<Object> getCurrencyDetailById(HttpServletRequest request,
                                                        @RequestParam String id) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        String plant = claimsDao.getPlt();
        String sub = claimsDao.getSub();
        String empId = claimsDao.getEid();
        String formattedDate = new DateTimeCalc().getTodayDate();
        String formattedTime= new DateTimeCalc().getTodayDateTime();
        CurrencyMst currencyMst = currencyService.getCurrencyDetailById(id);
        return new ResponseEntity<>(currencyMst, HttpStatus.OK);
    }
}
