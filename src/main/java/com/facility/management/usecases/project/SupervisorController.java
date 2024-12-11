package com.facility.management.usecases.project;

import com.facility.management.helpers.common.calc.DateTimeCalc;
import com.facility.management.helpers.common.results.ResultDao;
import com.facility.management.helpers.common.token.ClaimsDao;
import com.facility.management.helpers.common.token.ClaimsSet;
import com.facility.management.helpers.configs.LoggerConfig;
import com.facility.management.persistence.models.*;
import com.facility.management.usecases.employee_master.EmployeeMasterService;
import com.facility.management.usecases.inventory.InventoryService;
import com.facility.management.usecases.project.finproject.FinProjectService;
import com.facility.management.usecases.project.finprojectitem.FinProjectItemService;
import com.facility.management.usecases.project.finprojectworkquotdetlevelroom.FinProjectWorkQuotDetLevelRoomService;
import com.facility.management.usecases.project.finprojetworkdet.FinProjectWorkDetService;
import com.facility.management.usecases.project.finworkquotdet.FinProjectWorkQuotDetService;
import com.facility.management.usecases.project.project_stock_request.ProjectStockRequestDETService;
import com.facility.management.usecases.project.project_stock_request.ProjectStockRequestHDRService;
import com.facility.management.usecases.project.projectallocation.ProjectWorkAllocationDETService;
import com.facility.management.usecases.project.projectallocation.ProjectWorkAllocationHDRService;
import com.facility.management.usecases.project.projectpojo.FinProjectWorkQuotDetLevelRoomPojo;
import com.facility.management.usecases.project.projectpojo.FinProjectWorkQuotDetPojo;
import com.facility.management.usecases.project.projectworkemployee.ProjectWorkEmployeeService;
import com.facility.management.usecases.project.workerchecklistdet.WorkerCheckListDetService;
import com.facility.management.usecases.project.worktypecategory.WorkTypCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("${spring.base.path}")
public class SupervisorController {

    @Autowired
    ClaimsSet claimsSet;

    @Autowired
    EmployeeMasterService employeeMasterService;

    @Autowired
    FinProjectService finProjectService;

    @Autowired
    FinProjectItemService finProjectItemService;

    @Autowired
    FinProjectWorkDetService finProjectWorkDetService;

    @Autowired
    FinProjectWorkQuotDetService FinProjectWorkQuotDetService;

    @Autowired
    ProjectWorkEmployeeService projectWorkEmployeeService;

    @Autowired
    ProjectWorkAllocationHDRService projectWorkAllocationHDRService;

    @Autowired
    ProjectWorkAllocationDETService projectWorkAllocationDETService;

    @Autowired
    ProjectStockRequestHDRService projectStockRequestHDRService;

    @Autowired
    ProjectStockRequestDETService projectStockRequestDETService;

    @Autowired
    FinProjectWorkQuotDetLevelRoomService finProjectWorkQuotDetLevelRoomService;

    @Autowired
    WorkTypCategoryService workTypCategoryService;

    @Autowired
    WorkerCheckListDetService workerCheckListDetService;

    @Autowired
    InventoryService inventoryService;


    @GetMapping("/getEmployeeByEmpcode")
    public ResponseEntity<Object> getEmployeeByEmpcode(HttpServletRequest request,@RequestParam String empcode) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        String plant = claimsDao.getPlt();
        LoggerConfig.logger.info("get employee by employee code started for " + plant);
        List<EmployeeMaster> employeeMaster = employeeMasterService.findByEmpCode(empcode);
        ResultDao resultdao = new ResultDao();
        resultdao.setResults(employeeMaster.get(0));
        return new ResponseEntity<>(resultdao, HttpStatus.OK);
    }


    @GetMapping("/getAllProjectByEmpcode")
    public ResponseEntity<Object> getAllProjectByEmpcode(HttpServletRequest request,
                                                         @RequestParam String empcode,
                                                         @RequestParam int page,
                                                         @RequestParam int count) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        String plant = claimsDao.getPlt();
        LoggerConfig.logger.info("get all project by employee code started for " + plant);
        List<FinProject> FinProjectlist = finProjectService.getallproject(empcode,page,count);
        ResultDao resultdao = new ResultDao();
        resultdao.setResults(FinProjectlist);
        return new ResponseEntity<>(resultdao, HttpStatus.OK);
    }

    @GetMapping("/getProjectByStatus")
    public ResponseEntity<Object> getProjectByStatus(HttpServletRequest request,
                                                         @RequestParam String empcode,
                                                         @RequestParam String status,
                                                         @RequestParam int page,
                                                         @RequestParam int count) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        String plant = claimsDao.getPlt();
        LoggerConfig.logger.info("get project by employee code and status started for " + plant);
        List<FinProject> FinProjectlist = finProjectService.getprojectbystatus(empcode,status,page,count);
        ResultDao resultdao = new ResultDao();
        resultdao.setResults(FinProjectlist);
        return new ResponseEntity<>(resultdao, HttpStatus.OK);
    }

    @GetMapping("/getProjectById")
    public ResponseEntity<Object> getProjectById(HttpServletRequest request,
                                                     @RequestParam int id) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        String plant = claimsDao.getPlt();
        LoggerConfig.logger.info("get project by id started for " + plant);
        Optional<FinProject> finProject = finProjectService.getbyid(id);
        ResultDao resultdao = new ResultDao();
        resultdao.setResults(finProject);
        return new ResponseEntity<>(resultdao, HttpStatus.OK);
    }

    @GetMapping("/getAllItemByProject")
    public ResponseEntity<Object> getItemByProject(HttpServletRequest request,
                                                     @RequestParam int pid,
                                                     @RequestParam int page,
                                                     @RequestParam int count) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        String plant = claimsDao.getPlt();
        LoggerConfig.logger.info("get item by project started for " + plant);
        List<FinProjectItemPojo> FinProjectItemPojolist = finProjectItemService.getallitembyProject(pid,page,count);
        ResultDao resultdao = new ResultDao();
        resultdao.setResults(FinProjectItemPojolist);
        return new ResponseEntity<>(resultdao, HttpStatus.OK);
    }

    @GetMapping("/getAllItemByProjectNoPage")
    public ResponseEntity<Object> getAllItemByProjectNoPage(HttpServletRequest request,
                                                   @RequestParam int pid) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        String plant = claimsDao.getPlt();
        LoggerConfig.logger.info("get item by project no page started for " + plant);
        List<FinProjectItemPojo> FinProjectItemPojolist = finProjectItemService.getallitembyProjectNoPage(pid);
        ResultDao resultdao = new ResultDao();
        resultdao.setResults(FinProjectItemPojolist);
        return new ResponseEntity<>(resultdao, HttpStatus.OK);
    }

    @GetMapping("/getAllWorkByProject")
    public ResponseEntity<Object> getAllWorkByProject(HttpServletRequest request,
                                                   @RequestParam int pid) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        String plant = claimsDao.getPlt();
        LoggerConfig.logger.info("get work by project started for " + plant);
        List<FinProjectWorkDet> FinProjectWorkDetlist = finProjectWorkDetService.getFinProjectWorkDetbyProject(pid);
        ResultDao resultdao = new ResultDao();
        resultdao.setResults(FinProjectWorkDetlist);
        return new ResponseEntity<>(resultdao, HttpStatus.OK);
    }

    @GetMapping("/getAllWorkQuoteByProject")
    public ResponseEntity<Object> getAllWorkQuoteByProject(HttpServletRequest request,
                                                      @RequestParam int pid) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        String plant = claimsDao.getPlt();
        LoggerConfig.logger.info("get work quote by project started for " + plant);
        List<FinProjectWorkQuotDet> FinProjectWorkQuotDetlist = FinProjectWorkQuotDetService.getFinProjectWorkQuotDetbyProject(pid);
        ResultDao resultdao = new ResultDao();
        resultdao.setResults(FinProjectWorkQuotDetlist);
        return new ResponseEntity<>(resultdao, HttpStatus.OK);
    }

    @GetMapping("/getWorkQuoteById")
    public ResponseEntity<Object> getWorkQuoteById(HttpServletRequest request,
                                                           @RequestParam int id) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        String plant = claimsDao.getPlt();
        LoggerConfig.logger.info("get work quote by project started for " + plant);
        FinProjectWorkQuotDet finProjectWorkQuotDet = FinProjectWorkQuotDetService.getFinProjectWorkQuotDetbyid(id);
        ResultDao resultdao = new ResultDao();
        resultdao.setResults(finProjectWorkQuotDet);
        return new ResponseEntity<>(resultdao, HttpStatus.OK);
    }

    @GetMapping("/getWorkTypeIdBYWorkQuoteId")
    public ResponseEntity<Object> getWorkTypeIdBYWorkQuoteId (HttpServletRequest request,
                                                      @RequestParam int id) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        String plant = claimsDao.getPlt();
        LoggerConfig.logger.info("get work quote by project started for " + plant);
        Integer val = FinProjectWorkQuotDetService.getWorktypeidbyid(id);
        ResultDao resultdao = new ResultDao();
        resultdao.setResults(val);
        return new ResponseEntity<>(resultdao, HttpStatus.OK);
    }



    @GetMapping("/getAllWorkQuoteByProjectWithLevel")
    public ResponseEntity<Object> getAllWorkQuoteByProjectWithLevel(HttpServletRequest request,
                                                           @RequestParam int pid) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        String plant = claimsDao.getPlt();
        LoggerConfig.logger.info("get work quote with level and room by project started for " + plant);
        List<FinProjectWorkQuotDet> FinProjectWorkQuotDetlist = FinProjectWorkQuotDetService.getFinProjectWorkQuotDetbyProject(pid);
        List<FinProjectWorkQuotDetPojo> FinProjectWorkQuotDetPojolist = new ArrayList<FinProjectWorkQuotDetPojo>();

        for(FinProjectWorkQuotDet finProjectWorkQuotDet:FinProjectWorkQuotDetlist){
            List<FinProjectWorkQuotDetLevelRoomPojo> workbylevelroom = finProjectWorkQuotDetLevelRoomService.getProWQDetLevelRoombyWkeypojo(finProjectWorkQuotDet.getWKEY());
            FinProjectWorkQuotDetPojo FinProjectWorkQuotDetPojo  = new FinProjectWorkQuotDetPojo();
            FinProjectWorkQuotDetPojo.setFinProjectWorkQuotDet(finProjectWorkQuotDet);
            FinProjectWorkQuotDetPojo.setWorkLevelRoomList(workbylevelroom);
            FinProjectWorkQuotDetPojolist.add(FinProjectWorkQuotDetPojo);
        }

        ResultDao resultdao = new ResultDao();
        resultdao.setResults(FinProjectWorkQuotDetPojolist);
        return new ResponseEntity<>(resultdao, HttpStatus.OK);
    }

    @GetMapping("/getAllEmployeeByProject")
    public ResponseEntity<Object> getAllEmployeeByProject(HttpServletRequest request,
                                                      @RequestParam String pno) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        String plant = claimsDao.getPlt();
        LoggerConfig.logger.info("get employee by project started for " + plant);
        List<ProjectWorkEmployee> ProjectWorkEmployeelist = projectWorkEmployeeService.getEmployeebyProject(pno);
        ResultDao resultdao = new ResultDao();
        resultdao.setResults(ProjectWorkEmployeelist);
        return new ResponseEntity<>(resultdao, HttpStatus.OK);
    }


    @GetMapping("/getAllEmployeeByWorkId")
    public ResponseEntity<Object> getAllEmployeeByWorkId(HttpServletRequest request,
                                                          @RequestParam String pno,@RequestParam int wid) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        String plant = claimsDao.getPlt();
        LoggerConfig.logger.info("get employee by project started for " + plant);
        List<ProjectWorkEmployee> ProjectWorkEmployeelist = projectWorkEmployeeService.getEmployeebyProjectByworkId(pno,wid);
        ResultDao resultdao = new ResultDao();
        resultdao.setResults(ProjectWorkEmployeelist);
        return new ResponseEntity<>(resultdao, HttpStatus.OK);
    }

    @GetMapping("/getAllEmployeeByWorkIdAndLnno")
    public ResponseEntity<Object> getAllEmployeeByWorkIdAndLnno(HttpServletRequest request,
                                                         @RequestParam String pno,@RequestParam int wid,@RequestParam int lnno) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        String plant = claimsDao.getPlt();
        LoggerConfig.logger.info("get employee by project started for " + plant);
        List<ProjectWorkEmployee> ProjectWorkEmployeelist = projectWorkEmployeeService.getEmployeebyProjectByworkIdANDLnno(pno,wid,lnno);
        ResultDao resultdao = new ResultDao();
        resultdao.setResults(ProjectWorkEmployeelist);
        return new ResponseEntity<>(resultdao, HttpStatus.OK);
    }

    @GetMapping("/getAllWorktypeCategory")
    public ResponseEntity<Object> getAllWorktypeCategory(HttpServletRequest request) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        String plant = claimsDao.getPlt();
        LoggerConfig.logger.info("get worktype category started for " + plant);
        List<WorkTypCategory>  val = workTypCategoryService.getall();
        ResultDao resultdao = new ResultDao();
        resultdao.setResults(val);
        return new ResponseEntity<>(resultdao, HttpStatus.OK);
    }

    @GetMapping("/getAllWorktypeCategoryByid")
    public ResponseEntity<Object> getAllWorktypeCategoryByid(HttpServletRequest request,@RequestParam int id) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        String plant = claimsDao.getPlt();
        LoggerConfig.logger.info("get worktype category by id started for " + plant);
        WorkTypCategory  val = workTypCategoryService.getbyid(id);
        ResultDao resultdao = new ResultDao();
        resultdao.setResults(val);
        return new ResponseEntity<>(resultdao, HttpStatus.OK);
    }

    @GetMapping("/CheckWorkAllocation")
    public ResponseEntity<Object> CheckWorkAllocation(HttpServletRequest request,
                                                      @RequestParam int pid,
                                                      @RequestParam int wid,@RequestParam String workdate) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        String plant = claimsDao.getPlt();
        LoggerConfig.logger.info("STARTING CheckWorkAllocation " + plant);
        List<ProjectWorkAllocationHDR> ProjectWorkAllocationHDRlist = projectWorkAllocationHDRService.getgetbyprojectanddate(pid,wid,workdate);
        ResultDao resultdao = new ResultDao();
        if(ProjectWorkAllocationHDRlist.size() > 0) {
            resultdao.setResults("OK");
        }else{
            resultdao.setResults("NOT OK");
        }
        return new ResponseEntity<>(resultdao, HttpStatus.OK);
    }

    @RequestMapping(value = "/SaveWorkAllocation", method = RequestMethod.POST)
    public ResponseEntity<?> SaveWorkAllocation(HttpServletRequest request, @RequestBody List<ProjectWorkAllocation> val) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        LoggerConfig.logger.info("STARTING setWorkAllocation");
        DateTimeCalc dateTimeCalc = new DateTimeCalc();
        String createdAt = dateTimeCalc.getUcloTodayDateTime();
        String createdBy = claimsDao.getEid();
        try{
            for (ProjectWorkAllocation projectWorkAllocation:  val) {
                int hdrid = projectWorkAllocationHDRService.saveHdr(projectWorkAllocation.getProjectWorkAllocationHDR());
                for (ProjectWorkAllocationDET projectWorkAllocationDET:projectWorkAllocation.getProjectWorkAllocationDETList()) {
                    projectWorkAllocationDET.setHDRID(hdrid);
                    projectWorkAllocationDETService.saveHDet(projectWorkAllocationDET);
                }
            }
            LoggerConfig.logger.info("ENDING setWorkAllocation");
            return new ResponseEntity<>("", HttpStatus.OK);
        }catch (Exception e) {
            LoggerConfig.logger.info("ERROR IN setWorkAllocation");
            LoggerConfig.logger.error(e.getMessage());
            //throw new Exception(e.getMessage());
            return new ResponseEntity<>("", HttpStatus.OK);
        }
    }

    @GetMapping("/getWorkAllocationById")
    public ResponseEntity<Object> getWorkAllocationById(HttpServletRequest request,@RequestParam int id) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        String plant = claimsDao.getPlt();
        LoggerConfig.logger.info(" Started getWorkAllocationById " + plant);
        ProjectWorkAllocation projectWorkAllocation = new ProjectWorkAllocation();
        Optional<ProjectWorkAllocationHDR> projectWorkAllocationHDR = projectWorkAllocationHDRService.getbyid(id);
        List<ProjectWorkAllocationDET> projectWorkAllocationDETList = projectWorkAllocationDETService.getbyhdrid(id);
        projectWorkAllocation.setProjectWorkAllocationHDR(projectWorkAllocationHDR.get());
        projectWorkAllocation.setProjectWorkAllocationDETList(projectWorkAllocationDETList);
        ResultDao resultdao = new ResultDao();
        resultdao.setResults(projectWorkAllocation);
        return new ResponseEntity<>(resultdao, HttpStatus.OK);
    }

    @RequestMapping(value = "/DeleteWorkAllocation", method = RequestMethod.POST)
    public ResponseEntity<?> DeleteWorkAllocation(HttpServletRequest request, @RequestParam int id) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        LoggerConfig.logger.info("STARTING DeleteWorkAllocation");
        DateTimeCalc dateTimeCalc = new DateTimeCalc();
        String createdAt = dateTimeCalc.getUcloTodayDateTime();
        String createdBy = claimsDao.getEid();
        try{
            projectWorkAllocationHDRService.deletebyid(id);
            projectWorkAllocationDETService.deletebyhdrid(id);
            LoggerConfig.logger.info("ENDING DeleteWorkAllocation");
            return new ResponseEntity<>("", HttpStatus.OK);
        }catch (Exception e) {
            LoggerConfig.logger.info("ERROR IN DeleteWorkAllocation");
            LoggerConfig.logger.error(e.getMessage());
            //throw new Exception(e.getMessage());
            return new ResponseEntity<>("", HttpStatus.OK);
        }
    }


    @RequestMapping(value = "/UpdateWorkAllocation", method = RequestMethod.POST)
    public ResponseEntity<?> UpdateWorkAllocation(HttpServletRequest request, @RequestParam int id,@RequestBody ProjectWorkAllocation val) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        LoggerConfig.logger.info("STARTING UpdateWorkAllocation");
        DateTimeCalc dateTimeCalc = new DateTimeCalc();
        String createdAt = dateTimeCalc.getUcloTodayDateTime();
        String createdBy = claimsDao.getEid();
        try{
            projectWorkAllocationHDRService.saveHdr(val.getProjectWorkAllocationHDR());
            projectWorkAllocationDETService.deletebyhdrid(id);
            projectWorkAllocationDETService.saveallHDet(val.getProjectWorkAllocationDETList());
            LoggerConfig.logger.info("ENDING UpdateWorkAllocation");
            return new ResponseEntity<>("", HttpStatus.OK);
        }catch (Exception e) {
            LoggerConfig.logger.info("ERROR IN UpdateWorkAllocation");
            LoggerConfig.logger.error(e.getMessage());
            //throw new Exception(e.getMessage());
            return new ResponseEntity<>("", HttpStatus.OK);
        }
    }

    @GetMapping("/getProjectWorkAllowcation")
    public ResponseEntity<Object> getProjectWorkAllowcation(HttpServletRequest request,
                                                   @RequestParam int pid, @RequestParam(required=false) String fromdate ,@RequestParam(required=false) String todate,
                                                   @RequestParam int page, @RequestParam int count) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        String plant = claimsDao.getPlt();
        LoggerConfig.logger.info("getProjectWorkAllowcation started for " + plant);
        List<ProjectWorkAllocation> projectWorkAllocationList = new ArrayList<ProjectWorkAllocation>();
        List<ProjectWorkAllocationHDR> projectWorkAllocationHDRList = new ArrayList<ProjectWorkAllocationHDR>();
        if(fromdate == null){
            fromdate="";
        }
        if(todate == null){
            todate="";
        }

        if(page > 0){
            page = page - 1;
        }
        page = page*count;

        if(fromdate.length() == 0 && todate.length() == 0){
            projectWorkAllocationHDRList = projectWorkAllocationHDRService.getallbyproject(pid,page,count);
        }else if(fromdate.length() > 0 && todate.length() == 0){
            projectWorkAllocationHDRList = projectWorkAllocationHDRService.getgetbyprojectanddatefdate(pid,fromdate,page,count);
        }else if(fromdate.length() == 0 && todate.length() > 0){
            projectWorkAllocationHDRList = projectWorkAllocationHDRService.getgetbyprojectanddatetdate(pid,todate,page,count);
        }else{
            projectWorkAllocationHDRList = projectWorkAllocationHDRService.getgetbyprojectanddate(pid,fromdate,todate,page,count);
        }

        for (ProjectWorkAllocationHDR projectWorkAllocationHDR: projectWorkAllocationHDRList) {
            ProjectWorkAllocation projectWorkAllocation = new ProjectWorkAllocation();
            projectWorkAllocation.setProjectWorkAllocationHDR(projectWorkAllocationHDR);
            List<ProjectWorkAllocationDET> projectWorkAllocationDETList = projectWorkAllocationDETService.getbyhdrid(projectWorkAllocationHDR.getID());
            projectWorkAllocation.setProjectWorkAllocationDETList(projectWorkAllocationDETList);
            projectWorkAllocationList.add(projectWorkAllocation);
        }


        ResultDao resultdao = new ResultDao();
        resultdao.setResults(projectWorkAllocationList);
        return new ResponseEntity<>(resultdao, HttpStatus.OK);
    }

    @RequestMapping(value = "/saveProjectStockRequest", method = RequestMethod.POST)
    public ResponseEntity<?> saveProjectStockRequest(HttpServletRequest request, @RequestBody List<ProjectStockRequest> val) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        LoggerConfig.logger.info("STARTING saveProjectStockRequest");
        DateTimeCalc dateTimeCalc = new DateTimeCalc();
        String createdAt = dateTimeCalc.getUcloTodayDateTime();
        String createdBy = claimsDao.getEid();
        try{
            for (ProjectStockRequest projectStockRequest:  val) {
                int hdrid = projectStockRequestHDRService.saveHdr(projectStockRequest.getProjectStockRequestHDR());
                for (ProjectStockRequestDET projectStockRequestDET:projectStockRequest.getProjectStockRequestDETList()) {
                    projectStockRequestDET.setHDRID(hdrid);
                    projectStockRequestDETService.saveDET(projectStockRequestDET);
                }
            }
            LoggerConfig.logger.info("ENDING saveProjectStockRequest");
            return new ResponseEntity<>("", HttpStatus.OK);
        }catch (Exception e) {
            LoggerConfig.logger.info("ERROR IN saveProjectStockRequest");
            LoggerConfig.logger.error(e.getMessage());
            //throw new Exception(e.getMessage());
            return new ResponseEntity<>("", HttpStatus.OK);
        }
    }

    @GetMapping("/getProjectStockRequestHdr")
    public ResponseEntity<Object> getProjectStockRequestHdr(HttpServletRequest request,@RequestParam int id) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        String plant = claimsDao.getPlt();
        LoggerConfig.logger.info(" Started getProjectStockRequestHdr " + plant);
        ProjectStockRequestHDR projectStockRequestHDR = projectStockRequestHDRService.getbyid(id);
        ResultDao resultdao = new ResultDao();
        resultdao.setResults(projectStockRequestHDR);
        return new ResponseEntity<>(resultdao, HttpStatus.OK);
    }

    @GetMapping("/getProjectStockRequestHdrByprojectId")
    public ResponseEntity<Object> getProjectStockRequestHdrByprojectId(HttpServletRequest request,@RequestParam int pid) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        String plant = claimsDao.getPlt();
        LoggerConfig.logger.info(" Started getProjectStockRequestHdr " + plant);
        List<ProjectStockRequestHDR> projectStockRequestHDRList = projectStockRequestHDRService.getByProject(pid);
        ResultDao resultdao = new ResultDao();
        resultdao.setResults(projectStockRequestHDRList);
        return new ResponseEntity<>(resultdao, HttpStatus.OK);
    }

    @GetMapping("/getProjectStockRequestDet")
    public ResponseEntity<Object> getProjectStockRequestDet(HttpServletRequest request,
                                                   @RequestParam int hid,
                                                   @RequestParam int page,
                                                   @RequestParam int count) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        String plant = claimsDao.getPlt();
        LoggerConfig.logger.info("get getProjectStockRequestDet started for " + plant);
        List<ProjectStockRequestDET> ProjectStockRequestDETlist = projectStockRequestDETService.getByhdrid(hid,page,count);
        ResultDao resultdao = new ResultDao();
        resultdao.setResults(ProjectStockRequestDETlist);
        return new ResponseEntity<>(resultdao, HttpStatus.OK);
    }

    @RequestMapping(value = "/DeleteProjectStockRequest", method = RequestMethod.POST)
    public ResponseEntity<?> DeleteProjectStockRequest(HttpServletRequest request, @RequestParam int id) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        LoggerConfig.logger.info("STARTING DeleteProjectStockRequest");
        DateTimeCalc dateTimeCalc = new DateTimeCalc();
        String createdAt = dateTimeCalc.getUcloTodayDateTime();
        String createdBy = claimsDao.getEid();
        try{
            projectStockRequestHDRService.deleteHdr(id);
            projectStockRequestDETService.deletebyhdrid(id);
            LoggerConfig.logger.info("ENDING DeleteProjectStockRequest");
            return new ResponseEntity<>("", HttpStatus.OK);
        }catch (Exception e) {
            LoggerConfig.logger.info("ERROR IN DeleteProjectStockRequest");
            LoggerConfig.logger.error(e.getMessage());
            //throw new Exception(e.getMessage());
            return new ResponseEntity<>("", HttpStatus.OK);
        }
    }


    @RequestMapping(value = "/UpdateProjectStockRequest", method = RequestMethod.POST)
    public ResponseEntity<?> UpdateWorkAllocation(HttpServletRequest request, @RequestParam int id,@RequestBody ProjectStockRequest projectStockRequest) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        LoggerConfig.logger.info("STARTING UpdateProjectStockRequest");
        DateTimeCalc dateTimeCalc = new DateTimeCalc();
        String createdAt = dateTimeCalc.getUcloTodayDateTime();
        String createdBy = claimsDao.getEid();
        try{
            projectStockRequestHDRService.saveHdr(projectStockRequest.getProjectStockRequestHDR());
            projectStockRequestDETService.deletebyhdrid(projectStockRequest.getProjectStockRequestHDR().getID());
            for (ProjectStockRequestDET projectStockRequestDET:projectStockRequest.getProjectStockRequestDETList()) {
                projectStockRequestDET.setHDRID(projectStockRequest.getProjectStockRequestHDR().getID());
                projectStockRequestDETService.saveDET(projectStockRequestDET);
            }
            LoggerConfig.logger.info("ENDING UpdateProjectStockRequest");
            return new ResponseEntity<>("", HttpStatus.OK);
        }catch (Exception e) {
            LoggerConfig.logger.info("ERROR IN UpdateProjectStockRequest");
            LoggerConfig.logger.error(e.getMessage());
            //throw new Exception(e.getMessage());
            return new ResponseEntity<>("", HttpStatus.OK);
        }
    }

    @GetMapping("/getWorkerCheckListByHdrid")
    public ResponseEntity<Object> getWorkerCheckListByHdrid(HttpServletRequest request,@RequestParam int hdrid) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        String plant = claimsDao.getPlt();
        LoggerConfig.logger.info(" Started getProjectStockRequestHdr " + plant);
        List<WorkerCheckListDet> val = workerCheckListDetService.getbyhdrid(hdrid);
        ResultDao resultdao = new ResultDao();
        resultdao.setResults(val);
        return new ResponseEntity<>(resultdao, HttpStatus.OK);
    }

    @GetMapping("/getRecivedProducts")
    public ResponseEntity<Object> getRecivedProducts(HttpServletRequest request,
                                                 @RequestParam int id) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        String plant = claimsDao.getPlt();
        LoggerConfig.logger.info("get getRecivedProducts by project id started for " + plant);
        Optional<FinProject> finProject = finProjectService.getbyid(id);
        String location="SHIPPINGAREA_"+finProject.get().getLOC();
        List<InvMst> InvMstlist = inventoryService.findallByLoc(plant,location);
        ResultDao resultdao = new ResultDao();
        resultdao.setResults(InvMstlist);
        return new ResponseEntity<>(resultdao, HttpStatus.OK);
    }

}
