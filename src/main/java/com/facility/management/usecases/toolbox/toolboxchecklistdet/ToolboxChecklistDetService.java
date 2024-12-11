package com.facility.management.usecases.toolbox.toolboxchecklistdet;

import com.facility.management.persistence.models.ProjectWorkAllocationHDR;
import com.facility.management.persistence.models.ToolboxChecklistDet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ToolboxChecklistDetService {

    @Autowired
    ToolboxChecklistDetRepository toolboxChecklistDetRepository;

    public int saveDet(ToolboxChecklistDet toolboxChecklistDet) throws Exception {
        int id = 1;
        try {
            toolboxChecklistDetRepository.save(toolboxChecklistDet);
        }catch (Exception e){
            throw new Exception(e);
        }
        return id;
    }

    public boolean saveListDet(List<ToolboxChecklistDet> toolboxChecklistDet) throws Exception {
        boolean val=false;
        try {
            toolboxChecklistDetRepository.saveAll(toolboxChecklistDet);
            val = true;
        }catch (Exception e){
            throw new Exception(e);
        }
        return val;
    }

    public ToolboxChecklistDet getbyid(int id) throws Exception {
        ToolboxChecklistDet getVal;
        try {
            getVal = toolboxChecklistDetRepository.getbyid(id);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return getVal;
    }

    public Boolean deletebyid(Integer pk0) throws Exception {
        try {
            toolboxChecklistDetRepository.deleteById(pk0);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return true;
    }

    public List<ToolboxChecklistDet> getall() throws Exception {
        List<ToolboxChecklistDet> getVal = new ArrayList<ToolboxChecklistDet>();
        try {
            getVal=toolboxChecklistDetRepository.findAll();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return getVal;
    }

    public List<ToolboxChecklistDet> getByhdrId(int hdrid) throws Exception {
        List<ToolboxChecklistDet> getVal = new ArrayList<ToolboxChecklistDet>();
        try {
            getVal=toolboxChecklistDetRepository.getbyhdrid(hdrid);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return getVal;
    }

}
