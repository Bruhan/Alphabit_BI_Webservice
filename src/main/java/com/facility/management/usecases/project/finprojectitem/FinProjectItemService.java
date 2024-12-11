package com.facility.management.usecases.project.finprojectitem;

import com.facility.management.persistence.models.FinProject;
import com.facility.management.persistence.models.FinProjectItemPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FinProjectItemService {
    @Autowired
    FinProjectItemRepository finProjectItemRepository;

    public List<FinProjectItemPojo> getallitembyProject(int pid, int page, int pcount) throws Exception {
        List<FinProjectItemPojo> getVal = new ArrayList<FinProjectItemPojo>();
        try {
            if(page > 0){
                page = page - 1;
            }
            page = page*pcount;
            getVal=finProjectItemRepository.getallitembyProject(pid,page,pcount);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return getVal;
    }

    public List<FinProjectItemPojo> getallitembyProjectNoPage(int pid) throws Exception {
        List<FinProjectItemPojo> getVal = new ArrayList<FinProjectItemPojo>();
        try {
            getVal=finProjectItemRepository.getallitembyProjectNoPage(pid);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return getVal;
    }
}
