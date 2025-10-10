package com.owner.process.usecases.project.projectallocation;

import com.owner.process.persistence.models.ProjectWorkAllocationDET;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProjectWorkAllocationDETService {

    @Autowired
    ProjectWorkAllocationDETRepository projectWorkAllocationDETRepository;


    public int saveHDet(ProjectWorkAllocationDET projectWorkAllocationDET) throws Exception {
        int id;
        try {
            id = projectWorkAllocationDETRepository.save(projectWorkAllocationDET).getID();
        }catch (Exception e){
            throw new Exception(e);
        }
        return id;
    }

    public boolean saveallHDet(List<ProjectWorkAllocationDET> projectWorkAllocationDET) throws Exception {
        boolean val = true;
        try {
            projectWorkAllocationDETRepository.saveAll(projectWorkAllocationDET);
        }catch (Exception e){
            val=false;
            throw new Exception(e);
        }
        return val;
    }

    public Optional<ProjectWorkAllocationDET> getbyid(int id) throws Exception {
        Optional<ProjectWorkAllocationDET> getVal;
        try {
            getVal = projectWorkAllocationDETRepository.findById(id);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return getVal;
    }

    public Boolean deletebyid(Integer pk0) throws Exception {
        try {
            projectWorkAllocationDETRepository.deleteById(pk0);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return true;
    }

    public List<ProjectWorkAllocationDET> getbyhdrid(int hdrid) throws Exception {
        List<ProjectWorkAllocationDET> getVal = new ArrayList<ProjectWorkAllocationDET>();
        try {
            getVal=projectWorkAllocationDETRepository.getbyhdrid(hdrid);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return getVal;
    }

    public Boolean deletebyhdrid(Integer pk0) throws Exception {
        try {
            projectWorkAllocationDETRepository.deleteByHDRID(pk0);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return true;
    }

    public List<ProjectWorkAllocationDET> getbyhdridworkdateandempid(int hdrid,String workdate,String empid) throws Exception {
        List<ProjectWorkAllocationDET> getVal = new ArrayList<ProjectWorkAllocationDET>();
        try {
            getVal=projectWorkAllocationDETRepository.getbyhdridworkdateandempid(hdrid,workdate,empid);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return getVal;
    }
}
