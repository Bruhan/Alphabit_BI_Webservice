package com.facility.management.usecases.project;

import com.facility.management.helpers.exception.custom.ResourceNotFoundException;
import com.facility.management.usecases.project.dao.ProjectDao;
import com.facility.management.usecases.project.dto.ProjectDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {

    @Autowired
    ProjectDao projectDao;

    public List<ProjectDTO> getAllOpenProjects(String plant, String executiveNo) throws Exception {
        List<ProjectDTO> projectDTOList = null;
        try {
            projectDTOList = projectDao.getAllOpenProjects(plant, executiveNo);
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("ResourceNotFoundException");
        } catch (Exception e) {
            throw new Exception("SQL Error!!!");
        }
        return projectDTOList;
    }

}
