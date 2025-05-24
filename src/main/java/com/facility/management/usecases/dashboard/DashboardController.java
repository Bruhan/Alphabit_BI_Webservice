package com.facility.management.usecases.dashboard;

import com.facility.management.helpers.common.results.ResultDao;
import com.facility.management.helpers.common.token.ClaimsDao;
import com.facility.management.helpers.common.token.ClaimsSet;
import com.facility.management.usecases.dashboard.dto.*;
import com.facility.management.usecases.dashboard.enums.OWCMachineStatus;
import com.facility.management.usecases.wastage.enums.WastageType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("${spring.base.path}")
public class DashboardController {

    @Autowired
    ClaimsSet claimsSet;

    @Autowired
    DashboardService dashboardService;

    @GetMapping("/employee-detail/{employeeId}")
    public ResponseEntity<Object> getEmployeeDetails(HttpServletRequest request, @PathVariable("employeeId") String employeeId) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        String plant = claimsDao.getPlt();

        EmployeeDetailDTO employeeDetailDTO = dashboardService.getEmployeeDetails(plant, employeeId);
        ResultDao resultDao = new ResultDao();
        resultDao.setResults(employeeDetailDTO);
        resultDao.setStatusCode(HttpStatus.OK.value());
        resultDao.setMessage("SUCCESS");

        return new ResponseEntity<>(resultDao, HttpStatus.OK);
    }

    @GetMapping("/project-detail/{projectNo}")
    public ResponseEntity<Object> getProjectDetails(HttpServletRequest request, @PathVariable("projectNo") String projectNo) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        String plant = claimsDao.getPlt();

        ProjectDetailDTO projectDetailDTO = dashboardService.getProjectDetails(plant, projectNo);
        ResultDao resultDao = new ResultDao();
        resultDao.setResults(projectDetailDTO);
        resultDao.setStatusCode(HttpStatus.OK.value());
        resultDao.setMessage("SUCCESS");

        return new ResponseEntity<>(resultDao, HttpStatus.OK);
    }

    @GetMapping("/total-wastage/{projectNo}")
    public ResponseEntity<Object> getTotalWastage(HttpServletRequest request,
                                                  @PathVariable("projectNo") String projectNo,
                                                  @RequestParam String fromDate,
                                                  @RequestParam String toDate) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        String plant = claimsDao.getPlt();

        TotalWastageDTO totalWastageDTO = dashboardService.getTotalWastage(plant, projectNo, fromDate, toDate);

        ResultDao resultDao = new ResultDao();
        resultDao.setResults(totalWastageDTO);
        resultDao.setStatusCode(HttpStatus.OK.value());
        resultDao.setMessage("SUCCESS");

        return new ResponseEntity<>(resultDao, HttpStatus.OK);
    }

    @GetMapping("/total-organic-wastage/{projectNo}")
    public ResponseEntity<Object> getTotalOrganicWastage(HttpServletRequest request,
                                                         @PathVariable("projectNo") String projectNo,
                                                         @RequestParam String fromDate,
                                                         @RequestParam String toDate) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        String plant = claimsDao.getPlt();

        TotalWastageDTO totalWastageDTO = dashboardService.getTotalOrganicWastage(plant, projectNo, fromDate, toDate);

        ResultDao resultDao = new ResultDao();
        resultDao.setResults(totalWastageDTO);
        resultDao.setStatusCode(HttpStatus.OK.value());
        resultDao.setMessage("SUCCESS");

        return new ResponseEntity<>(resultDao, HttpStatus.OK);
    }

    @GetMapping("/total-inorganic-wastage/{projectNo}")
    public ResponseEntity<Object> getTotalInorganicWastage(HttpServletRequest request,
                                                         @PathVariable("projectNo") String projectNo,
                                                         @RequestParam String fromDate,
                                                         @RequestParam String toDate) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        String plant = claimsDao.getPlt();

        TotalWastageDTO totalWastageDTO = dashboardService.getTotalInorganicWastage(plant, projectNo, fromDate, toDate);

        ResultDao resultDao = new ResultDao();
        resultDao.setResults(totalWastageDTO);
        resultDao.setStatusCode(HttpStatus.OK.value());
        resultDao.setMessage("SUCCESS");

        return new ResponseEntity<>(resultDao, HttpStatus.OK);
    }

    @GetMapping("/total-rejected-wastage/{projectNo}")
    public ResponseEntity<Object> getTotalRejectedWastage(HttpServletRequest request,
                                                         @PathVariable("projectNo") String projectNo,
                                                         @RequestParam String fromDate,
                                                         @RequestParam String toDate) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        String plant = claimsDao.getPlt();

        TotalWastageDTO totalWastageDTO = dashboardService.getTotalRejectedWastage(plant, projectNo, fromDate, toDate);

        ResultDao resultDao = new ResultDao();
        resultDao.setResults(totalWastageDTO);
        resultDao.setStatusCode(HttpStatus.OK.value());
        resultDao.setMessage("SUCCESS");

        return new ResponseEntity<>(resultDao, HttpStatus.OK);
    }

    @GetMapping("/total-debris-wastage/{projectNo}")
    public ResponseEntity<Object> getTotalDebrisWastage(HttpServletRequest request,
                                                         @PathVariable("projectNo") String projectNo,
                                                         @RequestParam String fromDate,
                                                         @RequestParam String toDate) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        String plant = claimsDao.getPlt();

        TotalWastageDTO totalWastageDTO = dashboardService.getTotalDebrisWastage(plant, projectNo, fromDate, toDate);

        ResultDao resultDao = new ResultDao();
        resultDao.setResults(totalWastageDTO);
        resultDao.setStatusCode(HttpStatus.OK.value());
        resultDao.setMessage("SUCCESS");

        return new ResponseEntity<>(resultDao, HttpStatus.OK);
    }

    @GetMapping("/total-garden-wastage/{projectNo}")
    public ResponseEntity<Object> getTotalGardenWastage(HttpServletRequest request,
                                                         @PathVariable("projectNo") String projectNo,
                                                         @RequestParam String fromDate,
                                                         @RequestParam String toDate) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        String plant = claimsDao.getPlt();

        TotalWastageDTO totalWastageDTO = dashboardService.getTotalGardenWastage(plant, projectNo, fromDate, toDate);

        ResultDao resultDao = new ResultDao();
        resultDao.setResults(totalWastageDTO);
        resultDao.setStatusCode(HttpStatus.OK.value());
        resultDao.setMessage("SUCCESS");

        return new ResponseEntity<>(resultDao, HttpStatus.OK);
    }

    @GetMapping("/total-executive-wastage/{employeeId}")
    public ResponseEntity<Object> getTotalExecutiveWastage(HttpServletRequest request,
                                                           @PathVariable("employeeId") String employeeId,
                                                           @RequestParam String fromDate,
                                                           @RequestParam String toDate) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        String plant = claimsDao.getPlt();

        TotalWastageDTO totalWastageDTO = dashboardService.getTotalExecutiveWastage(plant, employeeId, fromDate, toDate);

        ResultDao resultDao = new ResultDao();
        resultDao.setResults(totalWastageDTO);
        resultDao.setStatusCode(HttpStatus.OK.value());
        resultDao.setMessage("SUCCESS");

        return new ResponseEntity<>(resultDao, HttpStatus.OK);
    }

    @GetMapping("/today-workers/{projectNo}")
    public ResponseEntity<Object> getTodayWorkers(HttpServletRequest request,
                                                  @PathVariable("projectNo") String projectNo) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        String plant = claimsDao.getPlt();

        TodayWorkerDTO totalTodayWorkers = dashboardService.getTodayWorkers(plant, projectNo);

        ResultDao resultDao = new ResultDao();
        resultDao.setResults(totalTodayWorkers);
        resultDao.setStatusCode(HttpStatus.OK.value());
        resultDao.setMessage("SUCCESS");

        return new ResponseEntity<>(resultDao, HttpStatus.OK);
    }

    @GetMapping("/total-product-requests/{projectNo}")
    public ResponseEntity<Object> getTotalProductRequests(HttpServletRequest request,
                                                          @PathVariable("projectNo") String projectNo) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        String plant = claimsDao.getPlt();

        TotalProductRequestDTO totalProductRequestDTO = dashboardService.getTotalProductRequests(plant, projectNo);

        ResultDao resultDao = new ResultDao();
        resultDao.setResults(totalProductRequestDTO);
        resultDao.setStatusCode(HttpStatus.OK.value());
        resultDao.setMessage("SUCCESS");

        return new ResponseEntity<>(resultDao, HttpStatus.OK);
    }

    @GetMapping("/owc-machines/{projectNo}")
    public ResponseEntity<Object> getOWCMachines(HttpServletRequest request,
                                                 @PathVariable("projectNo") String projectNo) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        String plant = claimsDao.getPlt();

        List<OWCMachineDetailDTO> owcMachineDetailDTOList = dashboardService.getOWCMachines(plant, projectNo);

        ResultDao resultDao = new ResultDao();
        resultDao.setResults(owcMachineDetailDTOList);
        resultDao.setStatusCode(HttpStatus.OK.value());
        resultDao.setMessage("SUCCESS");

        return new ResponseEntity<>(resultDao, HttpStatus.OK);
    }

    @PutMapping("/owc-machines/{projectNo}")
    public ResponseEntity<Object> changeOWCMachineStatus(HttpServletRequest request,
                                                         @PathVariable("projectNo") String projectNo, @RequestBody ChangeOWCMachineStatus changeOWCMachineStatus) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        String plant = claimsDao.getPlt();

        Integer result = dashboardService.changeOWCMachineStatus(plant, projectNo, changeOWCMachineStatus);

        ResultDao resultDao = new ResultDao();
        resultDao.setResults(result);
        resultDao.setStatusCode(HttpStatus.OK.value());
        resultDao.setMessage("SUCCESS");

        return new ResponseEntity<>(resultDao, HttpStatus.OK);
    }

}
