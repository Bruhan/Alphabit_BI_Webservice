package com.facility.management.usecases.employee_master.dao;

import com.facility.management.usecases.employee_master.dto.WorkerDTO;

import java.util.List;

public interface EmployeeMasterDao {
    public List<WorkerDTO> getAllWorkers(String plant);

    public WorkerDTO getWorkerByEmpNo(String plant, String empNo);

    List<WorkerDTO> getWorkersByProjectNo(String plant, String projectNo);
}
