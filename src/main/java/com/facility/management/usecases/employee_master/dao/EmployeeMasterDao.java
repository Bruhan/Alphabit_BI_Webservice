package com.facility.management.usecases.employee_master.dao;

import com.facility.management.persistence.models.EmployeeMaster;
import com.facility.management.usecases.employee_master.dto.WorkerDTO;

import java.util.List;

public interface EmployeeMasterDao {
    List<WorkerDTO> getAllWorkers(String plant);

    WorkerDTO getWorkerByEmpNo(String plant, String empNo);

    List<WorkerDTO> getWorkersByProjectNo(String plant, String projectNo);

}
