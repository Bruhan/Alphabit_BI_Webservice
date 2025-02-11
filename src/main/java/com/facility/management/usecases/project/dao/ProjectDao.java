package com.facility.management.usecases.project.dao;

import com.facility.management.persistence.models.EmployeeMaster;
import com.facility.management.usecases.project.dto.ProjectDTO;

import java.util.List;

public interface ProjectDao {
    List<ProjectDTO> getAllOpenExecutiveProjects(String plant, String executiveNo, String siteType);

    List<ProjectDTO> getAllOpenSupervisorProjects(String plant, String supervisorNo, String siteType);

    String getExecutiveByProjectNo(String plant, String projectNo);
}
