package com.owner.process.usecases.employee_master;

import com.owner.process.persistence.models.EmployeeMaster;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeMasterService {
    @Autowired
    EmployeeMasterRepository employeeMasterRepository;

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
}
