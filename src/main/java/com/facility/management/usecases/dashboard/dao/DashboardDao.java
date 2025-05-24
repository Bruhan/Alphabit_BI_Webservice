package com.facility.management.usecases.dashboard.dao;

import com.facility.management.usecases.dashboard.dto.*;
import com.facility.management.usecases.dashboard.enums.OWCMachineStatus;

import java.util.List;

public interface DashboardDao {
    EmployeeDetailDTO getEmployeeDetails(String plant, String employeeId);

    ProjectDetailDTO getProjectDetails(String plant, String projectNo);

    TotalWastageDTO getTotalWastage(String plant, String projectNo, String fromDate, String toDate);

    TotalWastageDTO getTotalExecutiveWastage(String plant, String employeeId, String fromDate, String toDate);

    TodayWorkerDTO getTodayWorkers(String plant, String projectNo);

    TotalProductRequestDTO getTotalProductRequests(String plant, String projectNo);

    List<OWCMachineDetailDTO> getOWCMachines(String plant, String projectNo);

    Integer changeOWCMachineStatus(String plant, String projectNo, ChangeOWCMachineStatus changeOWCMachineStatus);

    TotalWastageDTO getTotalOrganicWastage(String plant, String projectNo, String fromDate, String toDate);

    TotalWastageDTO getTotalInorganicWastage(String plant, String projectNo, String fromDate, String toDate);

    TotalWastageDTO getTotalRejectedWastage(String plant, String projectNo, String fromDate, String toDate);

    TotalWastageDTO getTotalDebrisWastage(String plant, String projectNo, String fromDate, String toDate);

    TotalWastageDTO getTotalGardenWastage(String plant, String projectNo, String fromDate, String toDate);
}
