package com.facility.management.usecases.wastage_movement;

import com.facility.management.helpers.common.calc.DateTimeCalc;
import com.facility.management.helpers.common.results.ResultDao;
import com.facility.management.helpers.common.token.ClaimsDao;
import com.facility.management.helpers.common.token.ClaimsSet;
import com.facility.management.usecases.attendance.dto.CalendarRequestDTO;
import com.facility.management.usecases.product_request.dto.PRCalendarResponseDTO;
import com.facility.management.usecases.wastage_movement.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

        WasteMovementSaveResponseDTO result = wasteMovementService.saveWasteMovement(plant, wasteMovementRequestDTO);

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

    @GetMapping("/generate-gatepass-number")
    public ResponseEntity<Object> generateGatePassNumber(HttpServletRequest request) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        String plant = claimsDao.getPlt();

        String gpNumber = wasteMovementService.generateGatePassNumber(plant);

        ResultDao resultDao = new ResultDao();

        resultDao.setMessage("SUCCESS");
        resultDao.setStatusCode(HttpStatus.OK.value());
        resultDao.setResults(gpNumber);

        return new ResponseEntity<>(resultDao, HttpStatus.OK);
    }

    @PostMapping("/uploadGatePassSign")
    public ResponseEntity<String> uploadGatePassSign(HttpServletRequest request, @RequestParam("file") MultipartFile file, @RequestParam int hdrid) {
        if (file.isEmpty()) {
            return new ResponseEntity<>("Please select a file to upload", HttpStatus.BAD_REQUEST);
        }

        try {
            ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
            String createdAt = new DateTimeCalc().getTodayDateTime();
            String createdBy = claimsDao.getEid();
            String plant = claimsDao.getPlt();

            String UPLOAD_DIR = "C:\\ATTACHMENTS\\WASTAGE MOVEMENT\\" + plant + "\\GATEPASS SIGN\\";

            File directory = new File(UPLOAD_DIR);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            String fileName = UUID.randomUUID().toString() + "-" + file.getOriginalFilename();
            String filePath = UPLOAD_DIR + fileName;
            String filePaths = UPLOAD_DIR + file.getOriginalFilename();

            try {
                Path targetLocation = Paths.get(filePath);
                Files.copy(file.getInputStream(), targetLocation);
            } catch (Exception e) {
                throw new RuntimeException("Failed to store file " + fileName, e);
            }

            wasteMovementService.updateGatePassSign(plant, hdrid, filePaths);


            return ResponseEntity.ok().body("File uploaded successfully");
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to upload file: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping("/uploadDCSign")
    public ResponseEntity<String> uploadDCSign(HttpServletRequest request, @RequestParam("file") MultipartFile file, @RequestParam int hdrid, @RequestParam String customerId) {
        if (file.isEmpty()) {
            return new ResponseEntity<>("Please select a file to upload", HttpStatus.BAD_REQUEST);
        }

        try {
            ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
            String createdAt = new DateTimeCalc().getTodayDateTime();
            String createdBy = claimsDao.getEid();
            String plant = claimsDao.getPlt();

            String UPLOAD_DIR = "C:\\ATTACHMENTS\\WASTAGE MOVEMENT\\" + plant + "\\DC SIGN\\";

            File directory = new File(UPLOAD_DIR);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            String fileName = UUID.randomUUID().toString() + "-" + file.getOriginalFilename();
            String filePath = UPLOAD_DIR + fileName;
            String filePaths = UPLOAD_DIR + file.getOriginalFilename();

            try {
                Path targetLocation = Paths.get(filePath);
                Files.copy(file.getInputStream(), targetLocation);
            } catch (Exception e) {
                throw new RuntimeException("Failed to store file " + fileName, e);
            }

            wasteMovementService.updateDCSign(plant, hdrid, customerId, filePaths);


            return ResponseEntity.ok().body("File uploaded successfully");
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to upload file: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping(value = "/getimage", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<Resource> getWorkImage(HttpServletRequest request, @RequestParam String imagepath) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        String plant = claimsDao.getPlt();
        final ByteArrayResource inputStream = new ByteArrayResource(Files.readAllBytes(Paths.get(
                imagepath
        )));
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentLength(inputStream.contentLength())
                .body(inputStream);
    }

}
