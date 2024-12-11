package com.facility.management.usecases.project.worktypecategory;

import com.facility.management.persistence.models.ProjectWorkAllocationDET;
import com.facility.management.persistence.models.WorkTypCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WorkTypCategoryService {

    @Autowired
    WorkTypCategoryRepository workTypCategoryRepository;


    public List<WorkTypCategory> getall() throws Exception {
        List<WorkTypCategory> getVal = new ArrayList<WorkTypCategory>();
        try {
            getVal=workTypCategoryRepository.findAll();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return getVal;
    }

    public WorkTypCategory getbyid(int id) throws Exception {
        WorkTypCategory getVal = new WorkTypCategory();
        try {
            getVal=workTypCategoryRepository.getbyid(id);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return getVal;
    }
}
