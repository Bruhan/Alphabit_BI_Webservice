package com.owner.process.usecases.purchase_order.poDetApproval;

import com.owner.process.helpers.common.calc.DateTimeCalc;
import com.owner.process.helpers.common.token.ClaimsDao;
import com.owner.process.helpers.common.token.ClaimsSet;
import com.owner.process.persistence.models.CurrencyMst;
import com.owner.process.persistence.models.PoDetApproval;
import com.owner.process.usecases.table_control.TableControlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("${spring.base.path}")
public class PoDetApprovalController {

    @Autowired
    PoDetApprovalService poDetApprovalService;
    @Autowired
    ClaimsSet claimsSet;


    @GetMapping("/PoDetApproval/GelAll")
    public ResponseEntity<Object> getall(HttpServletRequest request) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        String plant = claimsDao.getPlt();
        String sub = claimsDao.getSub();
        String empId = claimsDao.getEid();

        List<PoDetApproval> poDetApproval = poDetApprovalService.getAllPoDetApproval();
        return new ResponseEntity<>(poDetApproval, HttpStatus.OK);
    }
}
