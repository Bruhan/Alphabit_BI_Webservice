package com.facility.management.usecases.wastage_movement;

import com.facility.management.helpers.common.results.ResultDao;
import com.facility.management.helpers.common.token.ClaimsDao;
import com.facility.management.helpers.common.token.ClaimsSet;
import com.facility.management.usecases.wastage_movement.dto.WasteMovementDTO;
import com.facility.management.usecases.wastage_movement.dto.WasteMovementRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("${spring.base.path}")
public class WasteMovementController {
    @Autowired
    ClaimsSet claimsSet;

    @Autowired
    WasteMovementService wasteMovementService;

    @PostMapping("wastage-movement")
    public ResponseEntity<Object> saveWasteMovement(HttpServletRequest request, @RequestBody WasteMovementRequestDTO wasteMovementRequestDTO) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        String plant = claimsDao.getPlt();
//        String plant = "test";

        HashMap<String, Integer> result = wasteMovementService.saveWasteMovement(plant, wasteMovementRequestDTO);

        ResultDao resultDao = new ResultDao();
        resultDao.setResults(result);
        resultDao.setStatusCode(HttpStatus.OK.value());
        resultDao.setMessage("SUCCESS");

        return new ResponseEntity<>(resultDao, HttpStatus.OK);
    }

    @GetMapping("wastage-movement-summary/{projectNo}")
    public ResponseEntity<Object> getWastageMovementSummary(HttpServletRequest request, @PathVariable("projectNo") String projectNo) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        String plant = claimsDao.getPlt();

        List<WasteMovementDTO> wasteMovementDTOList = wasteMovementService.getWastageMovementSummary(plant, projectNo);

        ResultDao resultDao = new ResultDao();
        resultDao.setResults(wasteMovementDTOList);
        resultDao.setStatusCode(HttpStatus.OK.value());
        resultDao.setMessage("SUCCESS");

        return new ResponseEntity<>(resultDao, HttpStatus.OK);
    }
}
