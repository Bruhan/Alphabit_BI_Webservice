package com.owner.process.usecases.sales_order.DoAttachmentApproval;

import com.owner.process.persistence.models.DoAttachmentApproval;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoAttachmentApprovalService {
    @Autowired
    DoAttachmentApprovalRepository doAttachmentApprovalRepository;

    public List<DoAttachmentApproval> getByUKey(String pk0) throws Exception{
        List<DoAttachmentApproval> getVal;
        try{
            getVal = doAttachmentApprovalRepository.findByUniqueKey(pk0);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return getVal;
    }

}
