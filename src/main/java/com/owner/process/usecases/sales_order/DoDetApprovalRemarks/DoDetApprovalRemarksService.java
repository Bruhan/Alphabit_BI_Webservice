package com.owner.process.usecases.sales_order.DoDetApprovalRemarks;

import com.owner.process.persistence.models.DoDetApprovalRemarks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoDetApprovalRemarksService {
    @Autowired
    DoDetApprovalRemarksRepository doDetApprovalRemarksRepository;



    public List<DoDetApprovalRemarks> getByUKey(String pk0) throws Exception{
        List<DoDetApprovalRemarks> getVal;
        try{
            getVal = doDetApprovalRemarksRepository.findByUniqueKey(pk0);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return getVal;
    }
}
