package com.facility.management.usecases.project;

import com.facility.management.helpers.common.calc.DateTimeCalc;
import com.facility.management.helpers.common.results.ResultDao;
import com.facility.management.helpers.common.token.ClaimsDao;
import com.facility.management.helpers.common.token.ClaimsSet;
import com.facility.management.helpers.configs.LoggerConfig;
import com.facility.management.persistence.models.FinProject;
import com.facility.management.persistence.models.ProjectStockRequest;
import com.facility.management.persistence.models.ProjectStockRequestDET;
import com.facility.management.persistence.models.ProjectStockRequestHDR;
import com.facility.management.usecases.employee_master.EmployeeMasterService;
import com.facility.management.usecases.project.finproject.FinProjectService;
import com.facility.management.usecases.project.project_stock_request.ProjectStockRequestHDRService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("${spring.base.path}")
public class ManagerController {

    @Autowired
    ClaimsSet claimsSet;

    @Autowired
    EmployeeMasterService employeeMasterService;

    @Autowired
    FinProjectService finProjectService;

    @Autowired
    ProjectStockRequestHDRService projectStockRequestHDRService;

    @GetMapping("/getAllProjectByEmpcodeForManager")
    public ResponseEntity<Object> getAllProjectByEmpcodeForManager(HttpServletRequest request,
                                                         @RequestParam String empcode,
                                                         @RequestParam int page,
                                                         @RequestParam int count) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        String plant = claimsDao.getPlt();
        LoggerConfig.logger.info("get all project by employee code for manager started for " + plant);
        List<FinProject> FinProjectlist = finProjectService.getallprojectformanger(empcode,page,count);
        ResultDao resultdao = new ResultDao();
        resultdao.setResults(FinProjectlist);
        return new ResponseEntity<>(resultdao, HttpStatus.OK);
    }

    @GetMapping("/getProjectByStatusForManager")
    public ResponseEntity<Object> getProjectByStatusForManager(HttpServletRequest request,
                                                     @RequestParam String empcode,
                                                     @RequestParam String status,
                                                     @RequestParam int page,
                                                     @RequestParam int count) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        String plant = claimsDao.getPlt();
        LoggerConfig.logger.info("get project by employee code and status for manager started for " + plant);
        List<FinProject> FinProjectlist = finProjectService.getprojectbystatusformanger(empcode,status,page,count);
        ResultDao resultdao = new ResultDao();
        resultdao.setResults(FinProjectlist);
        return new ResponseEntity<>(resultdao, HttpStatus.OK);
    }

    @GetMapping("/getallProjectStockRequestHdrByManager")
    public ResponseEntity<Object> getallProjectStockRequestHdrByManager(HttpServletRequest request,@RequestParam String Empcode) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        String plant = claimsDao.getPlt();
        LoggerConfig.logger.info(" Started getallProjectStockRequestHdrByManager " + plant);
        List<ProjectStockRequestHDR> projectStockRequestHDRList = projectStockRequestHDRService.getByManagerCode(Empcode);
        ResultDao resultdao = new ResultDao();
        resultdao.setResults(projectStockRequestHDRList);
        return new ResponseEntity<>(resultdao, HttpStatus.OK);
    }

    @GetMapping("/getProjectStockRequestHdrBystatus")
    public ResponseEntity<Object> getProjectStockRequestHdrBystatus(HttpServletRequest request,@RequestParam String Empcode,@RequestParam String status) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        String plant = claimsDao.getPlt();
        LoggerConfig.logger.info(" Started getProjectStockRequestHdrByManager " + plant);
        List<ProjectStockRequestHDR> projectStockRequestHDRList = projectStockRequestHDRService.getByManagerCodeWithSataus(Empcode,status);
        ResultDao resultdao = new ResultDao();
        resultdao.setResults(projectStockRequestHDRList);
        return new ResponseEntity<>(resultdao, HttpStatus.OK);
    }

    @GetMapping("/getProjectStockRequestHdrByManager")
    public ResponseEntity<Object> getProjectStockRequestHdrByManager(HttpServletRequest request,@RequestParam String Empcode) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        String plant = claimsDao.getPlt();
        LoggerConfig.logger.info(" Started getProjectStockRequestHdrByManager " + plant);
        List<ProjectStockRequestHDR> projectStockRequestHDRList = projectStockRequestHDRService.getByManagerCodeWithSataus(Empcode,"");
        ResultDao resultdao = new ResultDao();
        resultdao.setResults(projectStockRequestHDRList);
        return new ResponseEntity<>(resultdao, HttpStatus.OK);
    }

    @RequestMapping(value = "/ProjectStockApproval", method = RequestMethod.POST)
    public ResponseEntity<?> ProjectStockApproval(HttpServletRequest request, @RequestParam int id,
                                                  @RequestParam String approvalstatus,
                                                  @RequestParam String approvalremarks) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        LoggerConfig.logger.info("STARTING saveProjectStockRequest");
        DateTimeCalc dateTimeCalc = new DateTimeCalc();
        String createdAt = dateTimeCalc.getUcloTodayDateTime();
        String createdBy = claimsDao.getEid();
        try{
            ProjectStockRequestHDR projectStockRequestHDR = projectStockRequestHDRService.getbyid(id);
            projectStockRequestHDR.setAPPROVAL_STATUS(approvalstatus);
            projectStockRequestHDR.setAPPROVAL_DATE(new DateTimeCalc().getUcloTodayDate());
            projectStockRequestHDR.setAPPROVER_REMARKS(approvalremarks);
            projectStockRequestHDR.setREQUESTSTATUS("Open");
            projectStockRequestHDRService.saveHdr(projectStockRequestHDR);

            LoggerConfig.logger.info("ENDING ProjectStockApproval");
            return new ResponseEntity<>("OK", HttpStatus.OK);
        }catch (Exception e) {
            LoggerConfig.logger.info("ERROR IN ProjectStockApproval");
            LoggerConfig.logger.error(e.getMessage());
            //throw new Exception(e.getMessage());
            return new ResponseEntity<>("NOT OK", HttpStatus.OK);
        }
    }
}
