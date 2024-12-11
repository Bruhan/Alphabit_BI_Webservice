package com.facility.management.usecases.project.dao;

import com.facility.management.usecases.project.dto.ProjectDTO;

import java.util.List;

public interface ProjectDao {
    List<ProjectDTO> getAllOpenProjects(String plant, String executiveNo);
}
