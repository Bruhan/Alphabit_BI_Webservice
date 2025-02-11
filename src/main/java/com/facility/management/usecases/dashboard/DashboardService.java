package com.facility.management.usecases.dashboard;

import com.facility.management.usecases.dashboard.dao.DashboardDao;
import com.facility.management.usecases.dashboard.dto.*;
import com.facility.management.usecases.dashboard.enums.OWCMachineStatus;
import com.facility.management.usecases.project.dao.ProjectDao;
import com.facility.management.usecases.project.dto.ProjectDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DashboardService {

    @Autowired
    DashboardDao dashboardDao;

    @Autowired
    ProjectDao projectDao;

    public EmployeeDetailDTO getEmployeeDetails(String plant, String employeeId) {
        EmployeeDetailDTO employeeDetailDTO = null;
        try {
            employeeDetailDTO = dashboardDao.getEmployeeDetails(plant, employeeId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return employeeDetailDTO;
    }

    public ProjectDetailDTO getProjectDetails(String plant, String projectNo) {

        ProjectDetailDTO projectDetailDTO = null;
        try {
            projectDetailDTO = dashboardDao.getProjectDetails(plant, projectNo);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return projectDetailDTO;
    }

    public TotalWastageDTO getTotalWastage(String plant, String projectNo, String fromDate, String toDate) {
        TotalWastageDTO totalWastageDTO = null;
        try {
            totalWastageDTO = dashboardDao.getTotalWastage(plant, projectNo, fromDate, toDate);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return totalWastageDTO;
    }

    public TotalWastageDTO getTotalExecutiveWastage(String plant, String employeeId, String fromDate, String toDate) {
        TotalWastageDTO totalWastageDTO = null;
        try {
            totalWastageDTO = dashboardDao.getTotalExecutiveWastage(plant, employeeId, fromDate, toDate);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return totalWastageDTO;
    }

    public TodayWorkerDTO getTodayWorkers(String plant, String projectNo) {
        TodayWorkerDTO todayTotalWorkers = null;
        try {
            todayTotalWorkers = dashboardDao.getTodayWorkers(plant, projectNo);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return todayTotalWorkers;
    }

    public TotalProductRequestDTO getTotalProductRequests(String plant, String projectNo) {
        TotalProductRequestDTO totalProductRequestDTO = null;
        try {
            totalProductRequestDTO = dashboardDao.getTotalProductRequests(plant, projectNo);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return totalProductRequestDTO;
    }

    public List<OWCMachineDetailDTO> getOWCMachines(String plant, String projectNo) {
        List<OWCMachineDetailDTO> owcMachineDetailDTOList = null;
        try {
            owcMachineDetailDTOList = dashboardDao.getOWCMachines(plant, projectNo);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return owcMachineDetailDTOList;
    }


    public Integer changeOWCMachineStatus(String plant, String projectNo, ChangeOWCMachineStatus changeOWCMachineStatus) {
        Integer result = 0;
        try {
            result = dashboardDao.changeOWCMachineStatus(plant, projectNo, changeOWCMachineStatus);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        return result;
    }
}
