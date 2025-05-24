package com.facility.management.usecases.attendance;

import com.facility.management.helpers.common.results.ResultDao;
import com.facility.management.helpers.common.token.ClaimsDao;
import com.facility.management.helpers.common.token.ClaimsSet;
import com.facility.management.usecases.attendance.dto.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("${spring.base.path}")
public class AttendanceController {

    @Autowired
    ClaimsSet claimsSet;

    @Autowired
    AttendanceService attendanceService;

    @Value("${app.attendance.default-image}")
    String defaultImagePath;

//    @GetMapping("/attendance")
//    public ResponseEntity<Object> getAttendanceByCriteria(HttpServletRequest request,
//                                                          @RequestParam(required = false) String date,
//                                                          @RequestParam(required = false) String startDate,
//                                                          @RequestParam(required = false) String endDate,
//                                                          @RequestParam(required = false) String empNo) throws Exception {
//
//        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
//
//        String plant = claimsDao.getPlt();
////        String plant = "test";
//        List<AttendanceDTO> attendanceDTOList = attendanceService.getAttendanceByCriteria(plant, date, startDate, endDate, empNo);
//
//        return new ResponseEntity<>(attendanceDTOList, HttpStatus.OK);
//
//    }

    @GetMapping("/attendance-summary/{projectNo}")
    public ResponseEntity<Object> getAttendanceByCriteria(HttpServletRequest request,
                                                          @PathVariable("projectNo") String projectNo,
                                                          @RequestParam(required = false) String date,
                                                          @RequestParam(required = false) String startDate,
                                                          @RequestParam(required = false) String endDate,
                                                          @RequestParam(required = false) String empNo) throws Exception {

        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));

        String plant = claimsDao.getPlt();
//        String plant = "test";
        List<StaffAttendanceDetailDTO> staffAttendanceDetailDTOList = attendanceService.getStaffAttendanceByCriteria(plant, projectNo, date, startDate, endDate, empNo);


        return new ResponseEntity<>(staffAttendanceDetailDTOList, HttpStatus.OK);

    }

    @GetMapping("/attendance/{id}")
    public ResponseEntity<Object> getAttendanceById(HttpServletRequest request,
                                                    @PathVariable("id") int id) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        String plant = claimsDao.getPlt();

//        String plant = "test";
        AttendanceDTO attendanceDTO = attendanceService.getAttendanceById(plant, id);

        return new ResponseEntity<>(attendanceDTO, HttpStatus.OK);
    }

//    @PostMapping("/attendance")
//    public ResponseEntity<Object> saveAttendance(HttpServletRequest request, @RequestParam("attendance") String attendanceData,
//                                                 @RequestParam(value = "inTimeFace", required = false) MultipartFile inTimeFace,
//                                                 @RequestParam(value = "outTimeFace", required = false) MultipartFile outTimeFace) throws Exception {
//        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
//        String plant = claimsDao.getPlt();
////        String plant = "test";
//
//
//        ObjectMapper objectMapper = new ObjectMapper();
//        AttendanceDTO attendanceDTO = objectMapper.readValue(attendanceData, AttendanceDTO.class);
//
////        String defaultImagePath = "C:/U-CLO_IMAGE/ATTENDANCE/VSoftTech/Images/DEFAULT/default.jpg";
//
//        Path path = Paths.get(defaultImagePath);
//        if(inTimeFace != null) {
//            attendanceDTO.setInTimeFace(inTimeFace.getBytes());
//        } else {
//            byte[] defaultBytes = Files.readAllBytes(path);
//            attendanceDTO.setInTimeFace(defaultBytes);
//        }
//
//        if(outTimeFace != null) {
//            attendanceDTO.setOutTimeFace(outTimeFace.getBytes());
//        } else {
//            byte[] defaultBytes = Files.readAllBytes(path);
//            attendanceDTO.setOutTimeFace(defaultBytes);
//        }
//
//
//        Integer result = attendanceService.saveAttendance(plant, attendanceDTO);
//
//        return new ResponseEntity<>("Attendance Saved Successfully", HttpStatus.OK);
//    }

    @PostMapping("/attendance")
    public ResponseEntity<Object> saveAttendance(HttpServletRequest request, @RequestBody StaffAttendanceRequestDTO attendanceData) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        String plant = claimsDao.getPlt();
//        String plant = "test";
        Integer result = attendanceService.saveAttendance(plant, attendanceData);

        ResultDao resultDao = new ResultDao();
        resultDao.setMessage("Attendance Saved Successfully");
        resultDao.setResults(result);
        resultDao.setStatusCode(HttpStatus.OK.value());

        return new ResponseEntity<>(resultDao, HttpStatus.OK);
    }

    @PutMapping("/attendance/{empId}")
    public ResponseEntity<Object> updateAttendance(HttpServletRequest request,
                                                   @PathVariable("empId") int empId, @RequestBody UpdateStaffAttendanceRequestDTO attendanceData
                                                   ) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        String plant = claimsDao.getPlt();
//        String plant = "test";

        Integer result = attendanceService.updateAttendance(plant, empId, attendanceData);

        if(result == 0) {
            ResultDao resultDao = new ResultDao();
            resultDao.setMessage("Attendance Record Not Found");
            resultDao.setResults(result);
            resultDao.setStatusCode(HttpStatus.NOT_FOUND.value());

            return new ResponseEntity<>(resultDao, HttpStatus.OK);
        }

        ResultDao resultDao = new ResultDao();
        resultDao.setMessage("Attendance Updated Successfully");
        resultDao.setResults(result);
        resultDao.setStatusCode(HttpStatus.OK.value());

        return new ResponseEntity<>(resultDao, HttpStatus.OK);

    }

    @GetMapping("/attendance/today")
    public ResponseEntity<Object> getTodayAttendance(HttpServletRequest request) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        String plant = claimsDao.getPlt();
//        String plant = "test";

        List<AttendanceDTO> attendanceDTOList = attendanceService.getTodayAttendance(plant);

        return new ResponseEntity<>(attendanceDTOList, HttpStatus.OK);
    }

    @PostMapping("/project-worker")
    public ResponseEntity<Object> saveProjectWorker(HttpServletRequest request, @RequestBody  ProjectWorkerRequestDTO projectWorkerRequestDTO) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        String plant = claimsDao.getPlt();
//        String plant = "test";

        HashMap<String, Object> result = attendanceService.saveProjectWorker(plant, projectWorkerRequestDTO);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @PostMapping("/toggle-project-worker")
    public ResponseEntity<Object> toggleProjectWorker(HttpServletRequest request, @RequestBody ToggleProjectWorkerDTO toggleProjectWorkerDTO) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        String plant = claimsDao.getPlt();
//        String plant = "test";

        ResultDao result = attendanceService.toggleProjectWorker(plant, toggleProjectWorkerDTO);


        return new ResponseEntity<>(result, HttpStatus.OK);

    }

    @GetMapping("/project-worker/{projectNo}")
    public ResponseEntity<Object> getAllProjectWorkers(HttpServletRequest request, @PathVariable("projectNo") String projectNo) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        String plant = claimsDao.getPlt();
//        String plant = "test";

        List<ProjectWorkerDTO> projectWorkerDTOS = attendanceService.getAllProjectWorkers(plant, projectNo);

        ResultDao resultDao = new ResultDao();

        resultDao.setMessage("SUCCESS");
        resultDao.setStatusCode(HttpStatus.OK.value());
        resultDao.setResults(projectWorkerDTOS);

        return new ResponseEntity<>(resultDao, HttpStatus.OK);
    }

    @PutMapping("/hasAttendance/{projectNo}")
    public ResponseEntity<Object> hasAttendance(HttpServletRequest request, @PathVariable("projectNo") String projectNo, @RequestBody CalendarRequestDTO calendarRequestDTO) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        String plant = claimsDao.getPlt();

        List<AttendanceCalendarResponseDTO> result = attendanceService.hasAttendance(plant, projectNo, calendarRequestDTO);

        ResultDao resultDao = new ResultDao();

        resultDao.setMessage("SUCCESS");
        resultDao.setStatusCode(HttpStatus.OK.value());
        resultDao.setResults(result);

        return new ResponseEntity<>(resultDao, HttpStatus.OK);
    }

}
