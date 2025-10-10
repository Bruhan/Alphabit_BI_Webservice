package com.owner.process.usecases.purchase_order.poDetApproval;

import com.owner.process.persistence.models.DoDet;
import com.owner.process.persistence.models.PoDetApproval;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PoDetApprovalService {
    @Autowired
    PoDetApprovalRepository poDetApprovalRepository;

    public List<PoDetApproval> getAllPoDetApproval() throws Exception {
        List<PoDetApproval> getVal;
        try {
            getVal = poDetApprovalRepository.findAll();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return getVal;
    }

    public List<PoDetApproval> getPoDetApprovalBYUkey(String pk0) throws Exception {
        List<PoDetApproval> getVal;
        try {
            getVal = poDetApprovalRepository.findByUniqueKey(pk0);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return getVal;
    }

    public void savePoDetApproval(PoDetApproval poDetApproval) throws Exception {
        try {
            poDetApprovalRepository.save(poDetApproval);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
