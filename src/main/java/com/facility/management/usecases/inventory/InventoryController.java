package com.facility.management.usecases.inventory;

import com.facility.management.helpers.common.token.ClaimsDao;
import com.facility.management.helpers.common.token.ClaimsSet;
import com.facility.management.usecases.inventory.pojo.InventoryPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("${spring.base.path}")
public class InventoryController {
    @Autowired
    InventoryService inventoryService;
    @Autowired
    ClaimsSet claimsSet;

        @GetMapping("/get-location-all")
    public ResponseEntity<?> getLocation(HttpServletRequest request) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
       /* String locations = request.getHeader("location");*/
        List<InventoryPojo>  location = inventoryService.getLocation(claimsDao.getPlt());
        return  new ResponseEntity<>(location, HttpStatus.OK);
    }
}

