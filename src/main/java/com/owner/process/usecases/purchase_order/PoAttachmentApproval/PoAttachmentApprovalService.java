package com.owner.process.usecases.purchase_order.PoAttachmentApproval;

import com.owner.process.persistence.models.PoAttachmentApproval;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PoAttachmentApprovalService {
    @Autowired
    PoAttachmentApprovalRepository poAttachmentApprovalRepository;

    public List<PoAttachmentApproval> getByPoNo(String pk0) throws Exception{
        List<PoAttachmentApproval> getVal;
        try{
            getVal = poAttachmentApprovalRepository.findByPoNo(pk0);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return getVal;
    }

    public List<PoAttachmentApproval> getByUKey(String pk0) throws Exception{
        List<PoAttachmentApproval> getVal;
        try{
            getVal = poAttachmentApprovalRepository.findByUniqueKey(pk0);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return getVal;
    }

    public void save(PoAttachmentApproval poAttachmentApproval) throws Exception{
        try{
            poAttachmentApprovalRepository.save(poAttachmentApproval);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public void delete(PoAttachmentApproval poAttachmentApproval) throws Exception{
        try{
            poAttachmentApprovalRepository.delete(poAttachmentApproval);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public void deleteall(List<PoAttachmentApproval> poAttachmentApproval) throws Exception{
        try{
            poAttachmentApprovalRepository.deleteAll(poAttachmentApproval);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
