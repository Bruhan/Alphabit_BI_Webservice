package com.facility.management.usecases.wastage_movement;

import com.facility.management.helpers.common.results.ResultDao;
import com.facility.management.helpers.common.token.ClaimsDao;
import com.facility.management.helpers.common.token.ClaimsSet;
import com.facility.management.usecases.attendance.dto.CalendarRequestDTO;
import com.facility.management.usecases.product_request.dto.PRCalendarResponseDTO;
import com.facility.management.usecases.wastage_movement.dto.TransportCalendarResponseDTO;
import com.facility.management.usecases.wastage_movement.dto.UpdateWasteMovementRequestDTO;
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

    @PutMapping("wastage-movement/{id}")
    public ResponseEntity<Object> updateWasteMovement(HttpServletRequest request, @PathVariable("id") Integer id, @RequestBody UpdateWasteMovementRequestDTO updateWasteMovementRequestDTO) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        String plant = claimsDao.getPlt();

        Integer result = wasteMovementService.updateWasteMovement(plant, id, updateWasteMovementRequestDTO);

        ResultDao resultDao = new ResultDao();
        resultDao.setResults(result);
        resultDao.setStatusCode(HttpStatus.OK.value());
        resultDao.setMessage("SUCCESS");

        return new ResponseEntity<>(resultDao, HttpStatus.OK);
    }

    @GetMapping("wastage-movement-summary/{projectNo}")
    public ResponseEntity<Object> getWastageMovementSummary(HttpServletRequest request, @PathVariable("projectNo") String projectNo, @RequestParam(required = false) String date) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        String plant = claimsDao.getPlt();

        List<WasteMovementDTO> wasteMovementDTOList = wasteMovementService.getWastageMovementSummary(plant, projectNo, date);

        ResultDao resultDao = new ResultDao();
        resultDao.setResults(wasteMovementDTOList);
        resultDao.setStatusCode(HttpStatus.OK.value());
        resultDao.setMessage("SUCCESS");

        return new ResponseEntity<>(resultDao, HttpStatus.OK);
    }

    @PutMapping("/hasTransport/{projectNo}")
    public ResponseEntity<Object> hasTransport(HttpServletRequest request, @PathVariable("projectNo") String projectNo, @RequestBody CalendarRequestDTO calendarRequestDTO) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        String plant = claimsDao.getPlt();

        List<TransportCalendarResponseDTO> result = wasteMovementService.hasTransport(plant, projectNo, calendarRequestDTO);

        ResultDao resultDao = new ResultDao();

        resultDao.setMessage("SUCCESS");
        resultDao.setStatusCode(HttpStatus.OK.value());
        resultDao.setResults(result);

        return new ResponseEntity<>(resultDao, HttpStatus.OK);
    }

    @GetMapping("/generate-dc-number")
    public ResponseEntity<Object> generateDCNumber(HttpServletRequest request) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        String plant = claimsDao.getPlt();

        String dcNumber = wasteMovementService.generateDCNumber(plant);

        ResultDao resultDao = new ResultDao();

        resultDao.setMessage("SUCCESS");
        resultDao.setStatusCode(HttpStatus.OK.value());
        resultDao.setResults(dcNumber);

        return new ResponseEntity<>(resultDao, HttpStatus.OK);
    }

}
