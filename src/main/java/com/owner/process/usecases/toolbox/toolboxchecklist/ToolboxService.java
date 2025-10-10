package com.owner.process.usecases.toolbox.toolboxchecklist;

import com.owner.process.helpers.common.calc.DateTimeCalc;
import com.owner.process.helpers.configs.LoggerConfig;
import com.owner.process.persistence.models.*;
import com.owner.process.usecases.toolbox.model.ToolboxDao;
import com.owner.process.usecases.toolbox.pojo.ToolboxPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ToolboxService {
    @Autowired
    ToolboxRepository toolboxRepository;
    @Autowired
    SafetyRepository safetyRepository;
    @Autowired
    PredefinedSafetyRepository predefinedSafetyRepository;

    @Autowired
    ToolboxChecklistMstRepository toolboxChecklistMstRepository;

    @Autowired
    PredefinedcheklistcategoryRepository predefinedcheklistcategoryRepository;


    public List<ToolboxPojo> getAllToolboxList() throws Exception {
        List<ToolboxPojo> getVal = new ArrayList<ToolboxPojo>();
        try {
            getVal= toolboxRepository.findAllToolbox();
        } catch (Exception e) {
            throw new Exception(e);
        }
        return getVal;
    }

    public int checklistsave(ToolboxChecklistMst toolboxChecklistMst) throws Exception {
        int id;
        try {
            id = toolboxChecklistMstRepository.save(toolboxChecklistMst).getId();
        }catch (Exception e){
            throw new Exception(e);
        }
        return id;
    }

    public List<ToolboxChecklistMst> getBymeetingid(int meetingid) throws Exception {
        List<ToolboxChecklistMst> getVal = new ArrayList<ToolboxChecklistMst>();
        try {
            getVal=toolboxRepository.getBymeetingid(meetingid);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return getVal;
    }

    public ToolboxChecklistMst getById(int id) throws Exception {
        ToolboxChecklistMst toolboxChecklistMst;
        try {
            toolboxChecklistMst = toolboxRepository.findById(id);
        }catch (Exception e){
            throw new Exception(e);
        }
        return toolboxChecklistMst;
    }

    public List<ToolboxChecklistMst> getByEmployee(String empCode) throws Exception {
        List<ToolboxChecklistMst> toolboxChecklistMsts;
        try {
            toolboxChecklistMsts = toolboxRepository.findByemployeeCode(empCode);
        }catch (Exception e){
            throw new Exception(e);
        }
        return toolboxChecklistMsts;
    }

    public List<ToolboxChecklistMst> getBySupervisor(String supervisorCode) throws Exception {
        List<ToolboxChecklistMst> toolboxChecklistMsts;
        try {
            toolboxChecklistMsts = toolboxRepository.findBysupervisorCode(supervisorCode);
        }catch (Exception e){
            throw new Exception(e);
        }
        return toolboxChecklistMsts;
    }

    public String createToolbox(ToolboxDao ToolboxMstDao, String plant, String createdBy) {
        String createdAt = new DateTimeCalc().getUcloTodayDateTime();
        ToolboxChecklistMst ToolboxMst = new ToolboxChecklistMst();
        ToolboxMst.setPlant(plant);
        ToolboxMst.setId(0);
        ToolboxMst.setHdrdate(ToolboxMstDao.getHdrdate());
        ToolboxMst.setToolboxMeetingId(ToolboxMstDao.getToolboxMeetingId());
        ToolboxMst.setToolboxMeetingName(ToolboxMstDao.getToolboxMeetingName());
        ToolboxMst.setEmployeeCode(ToolboxMstDao.getEmployeeCode());
        ToolboxMst.setSupervisorCode(ToolboxMstDao.getSupervisorCode());
        ToolboxMst.setStatus(ToolboxMstDao.getStatus());
        ToolboxMst.setCrAt(createdAt);
        ToolboxMst.setCrBy(createdBy);
        ToolboxMst.setUpAt(createdAt);
        ToolboxMst.setUpBy(createdBy);
        toolboxRepository.save(ToolboxMst);
        return "TOOLBOX INSERTED";
    }

    public Boolean updateToolbox(ToolboxDao ToolboxMstDao, String plant,String createdBy) {
        String createdAt = new DateTimeCalc().getUcloTodayDateTime();
        int id = ToolboxMstDao.getId();
        if (id != 0) {
            ToolboxChecklistMst ToolboxMst = new ToolboxChecklistMst();
            ToolboxMst.setId(id);
            ToolboxMst.setPlant(plant);
            ToolboxMst.setHdrdate(ToolboxMstDao.getHdrdate());
            ToolboxMst.setToolboxMeetingId(ToolboxMstDao.getToolboxMeetingId());
            ToolboxMst.setToolboxMeetingName(ToolboxMstDao.getToolboxMeetingName());
            ToolboxMst.setEmployeeCode(ToolboxMstDao.getEmployeeCode());
            ToolboxMst.setSupervisorCode(ToolboxMstDao.getSupervisorCode());
            ToolboxMst.setStatus(ToolboxMstDao.getStatus());
            ToolboxMst.setCrAt(createdAt);
            ToolboxMst.setCrBy(createdBy);
            ToolboxMst.setUpAt(createdAt);
            ToolboxMst.setUpBy(createdBy);
            toolboxRepository.save(ToolboxMst);
            return true;
        } else {
            return false;
        }
    }

    public void deleteById(int id) throws Exception {
        try {
            toolboxRepository.deleteById(id);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }


    public List<SafetyMst> getAllSafety() throws Exception {
        List<SafetyMst> safetyMst;
        try {
            safetyMst = safetyRepository.findall();
            if (safetyMst != null)
            return safetyMst;
        }catch (Exception e){
            LoggerConfig.logger.error(e.getMessage());
            throw new Exception(e.getMessage());
        }
        return null;
    }

    public List<PredfineChecklistCategory> getAllprelistcategory() throws Exception {
        List<PredfineChecklistCategory> predfineChecklistCategory;
        try {
            predfineChecklistCategory = predefinedcheklistcategoryRepository.findall();
            if (predfineChecklistCategory != null)
                return predfineChecklistCategory;
        }catch (Exception e){
            LoggerConfig.logger.error(e.getMessage());
            throw new Exception(e.getMessage());
        }
        return null;
    }

    public List<PredefinedSafetyMst> getAllPredefinedSafety() throws Exception {
        List<PredefinedSafetyMst> predefinedSafetyMst;
        try {
            predefinedSafetyMst = predefinedSafetyRepository.findall();
            if (predefinedSafetyMst != null)
                return predefinedSafetyMst;
        }catch (Exception e){
            LoggerConfig.logger.error(e.getMessage());
            throw new Exception(e.getMessage());
        }
        return null;
    }

    public List<PredefinedSafetyMst> getPredefinedSafetybyCId(int id) throws Exception {
        List<PredefinedSafetyMst> predefinedSafetyMst;
        try {
            predefinedSafetyMst = predefinedSafetyRepository.getbycategoryid(id);
            if (predefinedSafetyMst != null)
                return predefinedSafetyMst;
        }catch (Exception e){
            LoggerConfig.logger.error(e.getMessage());
            throw new Exception(e.getMessage());
        }
        return null;
    }

}