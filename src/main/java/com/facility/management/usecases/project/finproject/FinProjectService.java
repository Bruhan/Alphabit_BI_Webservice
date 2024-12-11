package com.facility.management.usecases.project.finproject;

import com.facility.management.persistence.models.FinProject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FinProjectService {
    @Autowired
    FinProjectRepository finProjectRepository;

    public Optional<FinProject> getbyid(int id) throws Exception {
        Optional<FinProject> getVal;
        try {
            getVal = finProjectRepository.findById(id);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return getVal;
    }

    public List<FinProject> getallproject(String empcode,int page, int pcount) throws Exception {
        List<FinProject> getVal = new ArrayList<FinProject>();
        try {
            if(page > 0){
                page = page - 1;
            }
            page = page*pcount;
            getVal=finProjectRepository.getallproject(empcode,page,pcount);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return getVal;
    }
    public List<FinProject> getprojectbystatus(String empcode,String status,int page, int pcount) throws Exception {
        List<FinProject> getVal = new ArrayList<FinProject>();
        try {
            if(page > 0){
                page = page - 1;
            }
            page = page*pcount;
            getVal=finProjectRepository.getprojectbystatus(empcode,status,page,pcount);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return getVal;
    }

    public List<FinProject> getallprojectformanger(String empcode,int page, int pcount) throws Exception {
        List<FinProject> getVal = new ArrayList<FinProject>();
        try {
            if(page > 0){
                page = page - 1;
            }
            page = page*pcount;
            getVal=finProjectRepository.getallprojectformanger(empcode,page,pcount);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return getVal;
    }
    public List<FinProject> getprojectbystatusformanger(String empcode,String status,int page, int pcount) throws Exception {
        List<FinProject> getVal = new ArrayList<FinProject>();
        try {
            if(page > 0){
                page = page - 1;
            }
            page = page*pcount;
            getVal=finProjectRepository.getprojectbystatusformanger(empcode,status,page,pcount);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return getVal;
    }
}
