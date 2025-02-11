package com.facility.management.usecases.project;

import com.facility.management.helpers.exception.custom.ResourceNotFoundException;
import com.facility.management.persistence.models.EmployeeMaster;
import com.facility.management.usecases.dashboard.dto.EmployeeDetailDTO;
import com.facility.management.usecases.project.dao.ProjectDao;
import com.facility.management.usecases.project.dto.ProjectDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {

    @Autowired
    ProjectDao projectDao;

    public List<ProjectDTO> getAllOpenExecutiveProjects(String plant, String executiveNo, String siteType) throws Exception {
        List<ProjectDTO> projectDTOList = null;
        try {
            projectDTOList = projectDao.getAllOpenExecutiveProjects(plant, executiveNo, siteType);
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("ResourceNotFoundException");
        } catch (Exception e) {
            throw new Exception("SQL Error!!!");
        }
        return projectDTOList;
    }

    public List<ProjectDTO> getAllOpenSupervisorProjects(String plant, String supervisorNo, String siteType) throws Exception {
        List<ProjectDTO> projectDTOList = null;
        try {
            projectDTOList = projectDao.getAllOpenSupervisorProjects(plant, supervisorNo, siteType);
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("ResourceNotFoundException");
        } catch (Exception e) {
            throw new Exception("SQL Error!!!");
        }
        return projectDTOList;
    }

    public String getExecutiveByProjectNo(String plant, String projectNo) throws Exception {
        String executiveNo = null;
        try {
            executiveNo = projectDao.getExecutiveByProjectNo(plant, projectNo);
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("ResourceNotFoundException");
        } catch (Exception e) {
            throw new Exception("SQL Error!!!");
        }

        return executiveNo;
    }

}
