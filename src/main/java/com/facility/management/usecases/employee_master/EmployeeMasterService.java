package com.facility.management.usecases.employee_master;

import com.facility.management.persistence.models.EmployeeMaster;
import com.facility.management.usecases.employee_master.dao.EmployeeMasterDao;
import com.facility.management.usecases.employee_master.dto.WorkerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeMasterService {
    @Autowired
    EmployeeMasterRepository employeeMasterRepository;

    @Autowired
    EmployeeMasterDao employeeMasterDao;

    public List<WorkerDTO> getAllWorkers(String plant) throws Exception {
        List<WorkerDTO> workerDTOList;
        try {
            workerDTOList = employeeMasterDao.getAllWorkers(plant);
        }
        catch (Exception e) {
            throw new Exception(e);
        }

        return workerDTOList;
    }

    public WorkerDTO getWorkerByEmpNo(String plant, String empNo) throws Exception {
        WorkerDTO workerDTO = null;
        try {
            workerDTO = employeeMasterDao.getWorkerByEmpNo(plant, empNo);
        } catch (Exception e) {
            throw new Exception(e);
        }

        return workerDTO;
    }

    public EmployeeMaster getById(int id) throws Exception {
        EmployeeMaster employeeMaster;
        try {
            employeeMaster = employeeMasterRepository.findById(id);
        }catch (Exception e){
            throw new Exception(e);
        }
        return employeeMaster;
    }

    public List<EmployeeMaster> findByEmpCode(String empcode) throws Exception {
        List<EmployeeMaster> employeeMaster;
        try {
            employeeMaster = employeeMasterRepository.findByEmpCode(empcode);
        }catch (Exception e){
            throw new Exception(e);
        }
        return employeeMaster;
    }

    public List<WorkerDTO> getWorkersByProjectNo(String plant, String projectNo) throws Exception {
        List<WorkerDTO> workerDTOList;
        try {
            workerDTOList = employeeMasterDao.getWorkersByProjectNo(plant, projectNo);
        }
        catch (Exception e) {
            throw new Exception(e);
        }

        return workerDTOList;
    }
}
