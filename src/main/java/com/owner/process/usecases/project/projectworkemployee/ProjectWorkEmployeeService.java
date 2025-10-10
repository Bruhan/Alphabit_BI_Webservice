package com.owner.process.usecases.project.projectworkemployee;

import com.owner.process.persistence.models.ProjectWorkEmployee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectWorkEmployeeService {

    @Autowired
    ProjectWorkEmployeeRepository projectWorkEmployeeRepository;

    public List<ProjectWorkEmployee> getEmployeebyProject(String pno) throws Exception {
        List<ProjectWorkEmployee> getVal = new ArrayList<ProjectWorkEmployee>();
        try {
            getVal=projectWorkEmployeeRepository.getEmployeebyProject(pno);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return getVal;
    }

    public List<ProjectWorkEmployee> getEmployeebyProjectByworkId(String pno, int workid) throws Exception {
        List<ProjectWorkEmployee> getVal = new ArrayList<ProjectWorkEmployee>();
        try {
            getVal=projectWorkEmployeeRepository.getEmployeebyProjectByworkId(pno,workid);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return getVal;
    }

    public List<ProjectWorkEmployee> getEmployeebyProjectByworkIdANDLnno(String projectno, int workid, int lineno) throws Exception {
        List<ProjectWorkEmployee> getVal = new ArrayList<ProjectWorkEmployee>();
        try {
            getVal=projectWorkEmployeeRepository.getEmployeebyProjectByworkIdANDLnno(projectno,workid,lineno);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return getVal;
    }
}
