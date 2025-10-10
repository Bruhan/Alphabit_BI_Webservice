package com.owner.process.usecases.project.project_stock_request;

import com.owner.process.persistence.models.ProjectStockRequestHDR;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectStockRequestHDRService {
    @Autowired
    ProjectStockRequestHDRRepository projectStockRequestHDRRepository;

    public int saveHdr(ProjectStockRequestHDR projectStockRequestHDR) throws Exception {
        int id;
        try {
            id = projectStockRequestHDRRepository.save(projectStockRequestHDR).getID();
        }catch (Exception e){
            throw new Exception(e);
        }
        return id;
    }

    public ProjectStockRequestHDR getbyid(int id) throws Exception {
        ProjectStockRequestHDR getVal;
        try {
            getVal = projectStockRequestHDRRepository.getByid(id);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return getVal;
    }

    public List<ProjectStockRequestHDR> getByProject(int pid) throws Exception {
        List<ProjectStockRequestHDR> getVal;
        try {
            getVal = projectStockRequestHDRRepository.getByProject(pid);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return getVal;
    }

    public boolean deleteHdr(int id) throws Exception {
        boolean val =true;
        try {
            projectStockRequestHDRRepository.deleteById(id);
        }catch (Exception e){
            val = false;
            throw new Exception(e);
        }
        return val;
    }

    public List<ProjectStockRequestHDR> getByManagerCode(String empcode) throws Exception {
        List<ProjectStockRequestHDR> getVal;
        try {
            getVal = projectStockRequestHDRRepository.getByManagerCode(empcode);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return getVal;
    }

    public List<ProjectStockRequestHDR> getByManagerCodeWithSataus(String empcode,String status) throws Exception {
        List<ProjectStockRequestHDR> getVal;
        try {
            getVal = projectStockRequestHDRRepository.getByManagerCodeWithSataus(empcode,status);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return getVal;
    }

}
