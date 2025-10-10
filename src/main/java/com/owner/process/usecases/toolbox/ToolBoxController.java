package com.owner.process.usecases.toolbox;


import com.owner.process.helpers.common.calc.DateTimeCalc;
import com.owner.process.helpers.common.results.ResultDao;
import com.owner.process.helpers.common.results.ResultsDTO;
import com.owner.process.helpers.common.token.ClaimsDao;
import com.owner.process.helpers.common.token.ClaimsSet;
import com.owner.process.persistence.models.ToolBoxMeeting;
import com.owner.process.persistence.models.ToolboxChecklistDet;
import com.owner.process.persistence.models.ToolboxChecklistMst;
import com.owner.process.usecases.activity_log.ActivityLogService;
import com.owner.process.usecases.toolbox.pojo.ToolboxCheckListHdrDetPojo;
import com.owner.process.usecases.toolbox.pojo.ToolboxMeetingPojo;
import com.owner.process.usecases.toolbox.toolboxchecklist.ToolboxService;
import com.owner.process.usecases.toolbox.toolboxchecklistdet.ToolboxChecklistDetService;
import com.owner.process.usecases.toolbox.toolboxmeeting.ToolBoxMeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("${spring.base.path}")
public class ToolBoxController {

    @Autowired
    ToolboxService toolboxService;
    @Autowired
    ToolboxChecklistDetService toolboxChecklistDetService;
    @Autowired
    ToolBoxMeetingService toolBoxMeetingService;
    @Autowired
    ActivityLogService activityLogService;
    @Autowired
    ClaimsSet claimsSet;

    @PostMapping("/toolbox/create-toolbox-meeting")
    public ResponseEntity<?> createToolMeetingbox (HttpServletRequest request, @RequestBody ToolboxMeetingPojo toolboxMeetingPojo) throws Exception {
        ResultDao resultDao = new ResultDao();
        try{
            ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
            String plant = claimsDao.getPlt();
            String createdAt = new DateTimeCalc().getUcloTodayDateTime();
            String createdBy = claimsDao.getSub();

             ToolBoxMeeting toolBoxMeeting = toolboxMeetingPojo.getToolBoxMeeting();
            toolBoxMeeting.setCrAt(createdAt);
            toolBoxMeeting.setCrBy(createdBy);
            int meetingid = toolBoxMeetingService.saveMeeting(toolBoxMeeting);

            List<ToolboxCheckListHdrDetPojo> toolboxCheckListHdrDetPojo = toolboxMeetingPojo.getToolboxCheckListHdrDetPojo();
            for (ToolboxCheckListHdrDetPojo tbclhdp:toolboxCheckListHdrDetPojo) {
                ToolboxChecklistMst toolboxChecklistMst = tbclhdp.getToolboxChecklistMst();
                toolboxChecklistMst.setToolboxMeetingId(meetingid);
                toolboxChecklistMst.setCrAt(createdAt);
                toolboxChecklistMst.setCrBy(createdBy);
                int checklistid = toolboxService.checklistsave(toolboxChecklistMst);

                List<ToolboxChecklistDet> toolboxChecklistDetList = tbclhdp.getToolboxChecklistDetList();
                for (ToolboxChecklistDet toolboxChecklistDet1:toolboxChecklistDetList) {
                    toolboxChecklistDet1.setHdrid(checklistid);
                    toolboxChecklistDet1.setCrAt(createdAt);
                    toolboxChecklistDet1.setCrBy(createdBy);
                    toolboxChecklistDetService.saveDet(toolboxChecklistDet1);
                }
            }
            resultDao.setMessage("Toolbox Meeting Created");
        }catch (Exception e){
            resultDao.setMessage("Toolbox Meeting Not Created");
        }
        return new ResponseEntity<>(resultDao, HttpStatus.OK);
    }

    @GetMapping("/toolbox/get-by-id")
    public ResponseEntity<?> getToolboxByid(HttpServletRequest request, @RequestParam int id) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
        ToolboxMeetingPojo toolboxMeetingPojo = new ToolboxMeetingPojo();
        ToolBoxMeeting toolBoxMeeting = toolBoxMeetingService.getbyid(id);
        toolboxMeetingPojo.setToolBoxMeeting(toolBoxMeeting);

        List<ToolboxCheckListHdrDetPojo> toolboxCheckListHdrDetPojoList = new ArrayList<ToolboxCheckListHdrDetPojo>();
        List<ToolboxChecklistMst> toolboxChecklistMst = toolboxService.getBymeetingid(toolBoxMeeting.getId());
        for (ToolboxChecklistMst tbclm:toolboxChecklistMst) {
            ToolboxCheckListHdrDetPojo toolboxCheckListHdrDetPojo = new ToolboxCheckListHdrDetPojo();
            toolboxCheckListHdrDetPojo.setToolboxChecklistMst(tbclm);
            List<ToolboxChecklistDet> toolboxChecklistDetList = toolboxChecklistDetService.getByhdrId(tbclm.getId());
            toolboxCheckListHdrDetPojo.setToolboxChecklistDetList(toolboxChecklistDetList);
            toolboxCheckListHdrDetPojoList.add(toolboxCheckListHdrDetPojo);
        }
        toolboxMeetingPojo.setToolboxCheckListHdrDetPojo(toolboxCheckListHdrDetPojoList);

        ResultsDTO result = new ResultsDTO();
        result.setResults(toolboxMeetingPojo);
        result.setMessage("Toolbox meeting By Id");
        result.setPageNumber(1);
        result.setPageSize(1);
        return  new ResponseEntity<>(result, HttpStatus.OK);
    }


    @GetMapping("/toolbox/get-by-supervisor")
    public ResponseEntity<?> getToolboxBySupervisor(HttpServletRequest request, @RequestParam String supervisorCode) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));

        List<ToolboxMeetingPojo> toolboxMeetingPojoList = new ArrayList<ToolboxMeetingPojo>();
        List<ToolBoxMeeting> toolBoxMeetingList = toolBoxMeetingService.getBySupervisorCode(supervisorCode);
        for (ToolBoxMeeting toolBoxMeeting:toolBoxMeetingList) {
            ToolboxMeetingPojo toolboxMeetingPojo = new ToolboxMeetingPojo();
            toolboxMeetingPojo.setToolBoxMeeting(toolBoxMeeting);

            List<ToolboxCheckListHdrDetPojo> toolboxCheckListHdrDetPojoList = new ArrayList<ToolboxCheckListHdrDetPojo>();
            List<ToolboxChecklistMst> toolboxChecklistMst = toolboxService.getBymeetingid(toolBoxMeeting.getId());
            for (ToolboxChecklistMst tbclm:toolboxChecklistMst) {
                ToolboxCheckListHdrDetPojo toolboxCheckListHdrDetPojo = new ToolboxCheckListHdrDetPojo();
                toolboxCheckListHdrDetPojo.setToolboxChecklistMst(tbclm);
                List<ToolboxChecklistDet> toolboxChecklistDetList = toolboxChecklistDetService.getByhdrId(tbclm.getId());
                toolboxCheckListHdrDetPojo.setToolboxChecklistDetList(toolboxChecklistDetList);
                toolboxCheckListHdrDetPojoList.add(toolboxCheckListHdrDetPojo);
            }
            toolboxMeetingPojo.setToolboxCheckListHdrDetPojo(toolboxCheckListHdrDetPojoList);

            toolboxMeetingPojoList.add(toolboxMeetingPojo);
        }

        ResultsDTO result = new ResultsDTO();
        result.setResults(toolboxMeetingPojoList);
        result.setMessage("Toolbox meeting By SupervisorCode");
        result.setPageNumber(1);
        result.setPageSize(1);
        return  new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/toolbox/get-by-Project")
    public ResponseEntity<?> getToolboxByProject(HttpServletRequest request, @RequestParam String project) throws Exception {
        ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));

        List<ToolboxMeetingPojo> toolboxMeetingPojoList = new ArrayList<ToolboxMeetingPojo>();
        List<ToolBoxMeeting> toolBoxMeetingList = toolBoxMeetingService.getByProjectCode(project);
        for (ToolBoxMeeting toolBoxMeeting:toolBoxMeetingList) {
            ToolboxMeetingPojo toolboxMeetingPojo = new ToolboxMeetingPojo();
            toolboxMeetingPojo.setToolBoxMeeting(toolBoxMeeting);

            List<ToolboxCheckListHdrDetPojo> toolboxCheckListHdrDetPojoList = new ArrayList<ToolboxCheckListHdrDetPojo>();
            List<ToolboxChecklistMst> toolboxChecklistMst = toolboxService.getBymeetingid(toolBoxMeeting.getId());
            for (ToolboxChecklistMst tbclm:toolboxChecklistMst) {
                ToolboxCheckListHdrDetPojo toolboxCheckListHdrDetPojo = new ToolboxCheckListHdrDetPojo();
                toolboxCheckListHdrDetPojo.setToolboxChecklistMst(tbclm);
                List<ToolboxChecklistDet> toolboxChecklistDetList = toolboxChecklistDetService.getByhdrId(tbclm.getId());
                toolboxCheckListHdrDetPojo.setToolboxChecklistDetList(toolboxChecklistDetList);
                toolboxCheckListHdrDetPojoList.add(toolboxCheckListHdrDetPojo);
            }
            toolboxMeetingPojo.setToolboxCheckListHdrDetPojo(toolboxCheckListHdrDetPojoList);

            toolboxMeetingPojoList.add(toolboxMeetingPojo);
        }

        ResultsDTO result = new ResultsDTO();
        result.setResults(toolboxMeetingPojoList);
        result.setMessage("Toolbox meeting By SupervisorCode");
        result.setPageNumber(1);
        result.setPageSize(1);
        return  new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/toolbox/update-toolbox-meeting")
    public ResponseEntity<?> updateToolMeetingbox (HttpServletRequest request, @RequestBody ToolboxMeetingPojo toolboxMeetingPojo) throws Exception {
        ResultDao resultDao = new ResultDao();
        try{
            ClaimsDao claimsDao = claimsSet.getClaimsDetailsAfterSet(request.getHeader("Authorization"));
            String plant = claimsDao.getPlt();
            String createdAt = new DateTimeCalc().getUcloTodayDateTime();
            String createdBy = claimsDao.getSub();

            ToolBoxMeeting toolBoxMeeting = toolboxMeetingPojo.getToolBoxMeeting();
            toolBoxMeeting.setUpAt(createdAt);
            toolBoxMeeting.setUpBy(createdBy);
            int meetingid = toolBoxMeetingService.saveMeeting(toolBoxMeeting);

            List<ToolboxCheckListHdrDetPojo> toolboxCheckListHdrDetPojo = toolboxMeetingPojo.getToolboxCheckListHdrDetPojo();
            for (ToolboxCheckListHdrDetPojo tbclhdp:toolboxCheckListHdrDetPojo) {
                ToolboxChecklistMst toolboxChecklistMst = tbclhdp.getToolboxChecklistMst();
                toolboxChecklistMst.setToolboxMeetingId(meetingid);
                toolboxChecklistMst.setUpAt(createdAt);
                toolboxChecklistMst.setUpBy(createdBy);
                int checklistid = toolboxService.checklistsave(toolboxChecklistMst);

                List<ToolboxChecklistDet> toolboxChecklistDetList = tbclhdp.getToolboxChecklistDetList();
                for (ToolboxChecklistDet toolboxChecklistDet:toolboxChecklistDetList) {
                    toolboxChecklistDet.setHdrid(checklistid);
                    toolboxChecklistDet.setUpAt(createdAt);
                    toolboxChecklistDet.setUpBy(createdBy);
                    toolboxChecklistDetService.saveDet(toolboxChecklistDet);
                }
            }
            resultDao.setMessage("Toolbox Meeting Updated");
        }catch (Exception e){
            resultDao.setMessage("Toolbox Meeting Not Updated");
        }
        return new ResponseEntity<>(resultDao, HttpStatus.OK);
    }


}
