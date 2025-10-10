package com.owner.process.usecases.sales_order.doDetApproval;

import com.owner.process.persistence.models.DoDetApproval;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoDetApprovalService {
    @Autowired
    DoDetApprovalRepository doDetApprovalRepository;


    public List<DoDetApproval> getDoDetApprovalBYUkey(String pk0) throws Exception {
        List<DoDetApproval> getVal;
        try {
            getVal = doDetApprovalRepository.findByUniqueKey(pk0);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return getVal;
    }


}
