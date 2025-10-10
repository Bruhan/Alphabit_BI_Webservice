package com.owner.process.usecases.PltApprovalMatrix;

import com.owner.process.usecases.PltApprovalMatrix.pojo.PltApprovalMatrixPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PltApprovalMatrixService {

    @Autowired
    PltApprovalMatrixRepository pltApprovalMatrixRepository;


    public List<PltApprovalMatrixPojo> getPltSummarybyApprovalType(String plant, String type) throws Exception {
        List<PltApprovalMatrixPojo> getVal = new ArrayList<PltApprovalMatrixPojo>();
        try {
            getVal=pltApprovalMatrixRepository.getPltSummaryByType(plant,type);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return getVal;
    }
}
