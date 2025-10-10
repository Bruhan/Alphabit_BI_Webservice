package com.owner.process.usecases.toolbox.toolboxchecklist;

import com.owner.process.helpers.common.calc.DateTimeCalc;
import com.owner.process.helpers.common.results.ResultsDTO;
import com.owner.process.helpers.common.token.ClaimsDao;
import com.owner.process.helpers.common.token.ClaimsSet;
import com.owner.process.persistence.models.PredefinedSafetyMst;
import com.owner.process.persistence.models.PredfineChecklistCategory;
import com.owner.process.persistence.models.SafetyMst;
import com.owner.process.persistence.models.ToolboxChecklistMst;
import com.owner.process.usecases.activity_log.ActivityLogModel;
import com.owner.process.usecases.activity_log.ActivityLogService;
import com.owner.process.usecases.toolbox.model.ToolboxDao;
import com.owner.process.usecases.toolbox.pojo.ToolboxPojo;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("${spring.base.path}")
public class ToolboxCheckListMstController {
    @Autowired
    ToolboxService toolboxService;
    @Autowired
    ActivityLogService activityLogService;
    @Autowired
    ClaimsSet claimsSet;


    @GetMapping("/toolboxchecklist/get-all-toolbox")
    public ResponseEntity<?> getAllToolboxList(HttpServletRequest request, @RequestParam(required=false) String fromDate) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        String plant = claimsDao.getPlt();
        int fdate = 0;
        if(fromDate == null){
            fdate = 1;
        }
        List<ToolboxPojo> toolboxPojos = toolboxService.getAllToolboxList();
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        List<ToolboxDao> ToolboxsummaryList = modelMapper.map(toolboxPojos,new TypeToken<List<ToolboxDao>>() { }.getType());
        //result
        ResultsDTO resultsDTO = new ResultsDTO();
        resultsDTO.setResults(ToolboxsummaryList);
        return new ResponseEntity<>(resultsDTO, HttpStatus.OK);
    }


    @GetMapping("/toolboxchecklist/get-by-id")
    public ResponseEntity<?> getToolboxByid(HttpServletRequest request, @RequestParam int id) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        ToolboxChecklistMst toolboxList = toolboxService.getById(id);
        ResultsDTO result = new ResultsDTO();
        result.setResults(toolboxList);
        result.setMessage("Toolbox List By Id");
        result.setPageNumber(1);
        result.setPageSize(1);
        return  new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/toolboxchecklist/get-by-employee")
    public ResponseEntity<?> getToolboxByEmployee(HttpServletRequest request, @RequestParam String empCode) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        List<ToolboxChecklistMst> employeeList = toolboxService.getByEmployee(empCode);
        ResultsDTO result = new ResultsDTO();
        result.setResults(employeeList);
        result.setMessage("Toolbox List By Employee");
        result.setPageNumber(1);
        result.setPageSize(1);
        return  new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/toolboxchecklist/get-by-supervisor")
    public ResponseEntity<?> getToolboxBySupervisor(HttpServletRequest request, @RequestParam String supervisorCode) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        List<ToolboxChecklistMst> supervisorList = toolboxService.getBySupervisor(supervisorCode);
        ResultsDTO result = new ResultsDTO();
        result.setResults(supervisorList);
        result.setMessage("Toolbox List By SupervisorCode");
        result.setPageNumber(1);
        result.setPageSize(1);
        return  new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/toolboxchecklist/create-toolbox")
    public ResponseEntity<?> createToolbox (HttpServletRequest request, @RequestBody ToolboxDao toolboxDao) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        String plant = claimsDao.getPlt();
        String createdAt = new DateTimeCalc().getUcloTodayDateTime();
        String createdBy = claimsDao.getSub();
        String tranDate = new DateTimeCalc().getUcloTodayDateTime();//doubt
        String result = toolboxService.createToolbox(toolboxDao,plant,createdBy);
        ActivityLogModel activityLogModel = new ActivityLogModel();
        activityLogService.setActivityLogDetails(
                activityLogModel.setActivityLogModel(
                        claimsDao.getPlt(), "CREATE_TOOLBOX", "", "", "", 0.0, toolboxDao.getToolboxMeetingName(), tranDate, createdAt, createdBy, "CREATED"));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/toolboxchecklist/update-toolbox")
    public ResponseEntity<?> updateToolbox (HttpServletRequest request, @RequestBody ToolboxDao toolboxDao) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        String plant = claimsDao.getPlt();
        String createdAt = new DateTimeCalc().getUcloTodayDateTime();
        String createdBy = claimsDao.getSub();
        String tranDate = new DateTimeCalc().getUcloTodayDateTime();//doubt
        Boolean Result = toolboxService.updateToolbox(toolboxDao,plant,createdBy);
        ActivityLogModel activityLogModel = new ActivityLogModel();
        activityLogService.setActivityLogDetails(
                activityLogModel.setActivityLogModel(
                        claimsDao.getPlt(), "UPDATE_TOOLBOX", "", "", "", 0.0, toolboxDao.getToolboxMeetingName(), tranDate, createdAt, createdBy, "UPDATED"));
        Map<String,Boolean> result = new HashMap<String,Boolean>();
        result.put("status",Result);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    @PostMapping("/toolboxchecklist/delete")
    public ResponseEntity<?> deletetoolboxByid(HttpServletRequest request, @RequestParam int Id) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        Map<String, Boolean> result = new HashMap<>();
        toolboxService.deleteById(Id);
        result.put("status", true);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    @GetMapping("/safety/get-all")
    public ResponseEntity<?> getSafetyList(HttpServletRequest request) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        List<SafetyMst> safetyMst = toolboxService.getAllSafety();
        ResultsDTO result = new ResultsDTO();
        result.setResults(safetyMst);
        result.setMessage("Listing Out All Safety");
        result.setPageNumber(1);
        result.setPageSize(1);
        return  new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/predefinedsafetycategory/get-all")
    public ResponseEntity<?> getallpredefinedsafetycategory(HttpServletRequest request) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        List<PredfineChecklistCategory> predefinedSafetyMst = toolboxService.getAllprelistcategory();
        ResultsDTO result = new ResultsDTO();
        result.setResults(predefinedSafetyMst);
        result.setMessage("Listing Out All PredefinedSafety category");
        result.setPageNumber(1);
        result.setPageSize(1);
        return  new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/predefinedsafety/get-all")
    public ResponseEntity<?> getPredefinedSafetyList(HttpServletRequest request) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        List<PredefinedSafetyMst> predefinedSafetyMst = toolboxService.getAllPredefinedSafety();
        ResultsDTO result = new ResultsDTO();
        result.setResults(predefinedSafetyMst);
        result.setMessage("Listing Out All PredefinedSafety");
        result.setPageNumber(1);
        result.setPageSize(1);
        return  new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/predefinedsafety/get-by-categoryid")
    public ResponseEntity<?> getPredefinedSafetyList(HttpServletRequest request, @RequestParam int categoryId) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        List<PredefinedSafetyMst> predefinedSafetyMst = toolboxService.getPredefinedSafetybyCId(categoryId);
        ResultsDTO result = new ResultsDTO();
        result.setResults(predefinedSafetyMst);
        result.setMessage("Listing Out All PredefinedSafety");
        result.setPageNumber(1);
        result.setPageSize(1);
        return  new ResponseEntity<>(result, HttpStatus.OK);
    }

}
