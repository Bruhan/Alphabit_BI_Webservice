package com.owner.process.usecases.toolbox.toolboxmeeting;

import com.owner.process.persistence.models.ToolBoxMeeting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ToolBoxMeetingService {

    @Autowired
    ToolBoxMeetingRepository toolBoxMeetingRepository;

    public int saveMeeting(ToolBoxMeeting toolBoxMeeting) throws Exception {
        int id;
        try {
            id = toolBoxMeetingRepository.save(toolBoxMeeting).getId();
        }catch (Exception e){
            throw new Exception(e);
        }
        return id;
    }

    public boolean saveListMeeting(List<ToolBoxMeeting> toolBoxMeeting) throws Exception {
        boolean val=false;
        try {
            toolBoxMeetingRepository.saveAll(toolBoxMeeting);
            val = true;
        }catch (Exception e){
            throw new Exception(e);
        }
        return val;
    }

    public ToolBoxMeeting getbyid(int id) throws Exception {
        ToolBoxMeeting getVal;
        try {
            getVal = toolBoxMeetingRepository.getbyid(id);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return getVal;
    }

    public Boolean deletebyid(Integer pk0) throws Exception {
        try {
            toolBoxMeetingRepository.deleteById(pk0);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return true;
    }

    public List<ToolBoxMeeting> getall() throws Exception {
        List<ToolBoxMeeting> getVal = new ArrayList<ToolBoxMeeting>();
        try {
            getVal=toolBoxMeetingRepository.findAll();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return getVal;
    }

    public List<ToolBoxMeeting> getByProjectCode(String project) throws Exception {
        List<ToolBoxMeeting> getVal = new ArrayList<ToolBoxMeeting>();
        try {
            getVal=toolBoxMeetingRepository.getbyproject(project);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return getVal;
    }

    public List<ToolBoxMeeting> getBySupervisorCode(String empcode) throws Exception {
        List<ToolBoxMeeting> getVal = new ArrayList<ToolBoxMeeting>();
        try {
            getVal=toolBoxMeetingRepository.getBySupervisorCode(empcode);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return getVal;
    }
}
