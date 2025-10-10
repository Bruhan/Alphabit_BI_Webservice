package com.owner.process.usecases.purchase_order.PoDetApprovalRemarks;

import com.owner.process.persistence.models.PoDetApprovalRemarks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PoDetApprovalRemarksService {
    @Autowired
    PoDetApprovalRemarksRepository poDetApprovalRemarksRepository;

    public List<PoDetApprovalRemarks> getByPoNo(String pk0) throws Exception{
        List<PoDetApprovalRemarks> getVal;
        try{
            getVal = poDetApprovalRemarksRepository.findByPoNo(pk0);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return getVal;
    }

    public List<PoDetApprovalRemarks> getByUKey(String pk0) throws Exception{
        List<PoDetApprovalRemarks> getVal;
        try{
            getVal = poDetApprovalRemarksRepository.findByUniqueKey(pk0);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return getVal;
    }

    public void save(PoDetApprovalRemarks poDetApprovalRemarks) throws Exception{
        try{
            poDetApprovalRemarksRepository.save(poDetApprovalRemarks);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public void delete(PoDetApprovalRemarks poDetApprovalRemarks) throws Exception{
        try{
            poDetApprovalRemarksRepository.delete(poDetApprovalRemarks);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public void deleteall(List<PoDetApprovalRemarks> poDetApprovalRemarks) throws Exception{
        try{
            poDetApprovalRemarksRepository.deleteAll(poDetApprovalRemarks);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
