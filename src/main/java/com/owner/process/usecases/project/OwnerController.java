package com.owner.process.usecases.project;

import com.owner.process.helpers.common.results.ResultDao;
import com.owner.process.helpers.common.token.ClaimsDao;
import com.owner.process.helpers.common.token.ClaimsSet;
import com.owner.process.helpers.configs.LoggerConfig;
import com.owner.process.persistence.models.FinProject;
import com.owner.process.usecases.employee_master.EmployeeMasterService;
import com.owner.process.usecases.project.finproject.FinProjectService;
import com.owner.process.usecases.project.project_stock_request.ProjectStockRequestHDRService;
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
public class OwnerController {

    @Autowired
    ClaimsSet claimsSet;

    @Autowired
    EmployeeMasterService employeeMasterService;

    @Autowired
    FinProjectService finProjectService;

    @Autowired
    ProjectStockRequestHDRService projectStockRequestHDRService;

    @GetMapping("/getAllProject")
    public ResponseEntity<Object> getAllProject(HttpServletRequest request,
                                                                   @RequestParam int page,
                                                                   @RequestParam int count) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        String plant = claimsDao.getPlt();
        LoggerConfig.logger.info("get all project started for " + plant);
        List<FinProject> FinProjectlist = finProjectService.getallproject(page,count);
        ResultDao resultdao = new ResultDao();
        resultdao.setResults(FinProjectlist);
        return new ResponseEntity<>(resultdao, HttpStatus.OK);
    }

    @GetMapping("/getProjectByStatus")
    public ResponseEntity<Object> getProjectByStatus(HttpServletRequest request,
                                                               @RequestParam String status,
                                                               @RequestParam int page,
                                                               @RequestParam int count) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        String plant = claimsDao.getPlt();
        LoggerConfig.logger.info("get project by status started for " + plant);
        List<FinProject> FinProjectlist = finProjectService.getprojectbystatus(status,page,count);
        ResultDao resultdao = new ResultDao();
        resultdao.setResults(FinProjectlist);
        return new ResponseEntity<>(resultdao, HttpStatus.OK);
    }



}
