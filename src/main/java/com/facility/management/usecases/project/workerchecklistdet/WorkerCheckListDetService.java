package com.facility.management.usecases.project.workerchecklistdet;

import com.facility.management.persistence.models.WorkerCheckListDet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WorkerCheckListDetService {

    @Autowired
    WorkerCheckListDetRepository workerCheckListDetRepository;

    public boolean savelist(WorkerCheckListDet workerCheckListDet) throws Exception {
        boolean val=false;
        try {
            workerCheckListDetRepository.save(workerCheckListDet);
            val = true;
        }catch (Exception e){
            throw new Exception(e);
        }
        return val;
    }

    public boolean savealllist(List<WorkerCheckListDet> workerCheckListDet) throws Exception {
        boolean val=false;
        try {
            workerCheckListDetRepository.saveAll(workerCheckListDet);
            val = true;
        }catch (Exception e){
            throw new Exception(e);
        }
        return val;
    }

    public List<WorkerCheckListDet> getbyhdrid(int hdrid) throws Exception {
        List<WorkerCheckListDet> getVal = new ArrayList<WorkerCheckListDet>();
        try {
            getVal=workerCheckListDetRepository.getByHdrid(hdrid);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return getVal;
    }
}
