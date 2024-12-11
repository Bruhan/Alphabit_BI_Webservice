package com.facility.management.usecases.project.project_stock_request;

import com.facility.management.persistence.models.ProjectStockRequestDET;
import com.facility.management.persistence.models.ProjectStockRequestHDR;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectStockRequestDETService {
    @Autowired
    ProjectStockRequestDETRepository projectStockRequestDETRepository;

    public int saveDET(ProjectStockRequestDET projectStockRequestDET) throws Exception {
        int id;
        try {
            id = projectStockRequestDETRepository.save(projectStockRequestDET).getID();
        }catch (Exception e){
            throw new Exception(e);
        }
        return id;
    }

    public Optional<ProjectStockRequestDET> getbyid(int id) throws Exception {
        Optional<ProjectStockRequestDET> getVal;
        try {
            getVal = projectStockRequestDETRepository.findById(id);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return getVal;
    }

    public List<ProjectStockRequestDET> getByhdrid(int hid,int page, int pcount) throws Exception {
        List<ProjectStockRequestDET> getVal;
        try {
            if(page > 0){
                page = page - 1;
            }
            page = page*pcount;
            getVal = projectStockRequestDETRepository.getbyhdrid(hid,page,pcount);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return getVal;
    }

    public boolean deletedet(int id) throws Exception {
        boolean val =true;
        try {
            projectStockRequestDETRepository.deleteById(id);
        }catch (Exception e){
            val = false;
            throw new Exception(e);
        }
        return val;
    }

    public boolean deletebyhdrid(int hid) throws Exception {
        boolean val =true;
        try {
            projectStockRequestDETRepository.deleteByHDRID(hid);
        }catch (Exception e){
            val = false;
            throw new Exception(e);
        }
        return val;
    }
}
