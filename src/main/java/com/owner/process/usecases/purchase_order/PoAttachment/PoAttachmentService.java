package com.owner.process.usecases.purchase_order.PoAttachment;

import com.owner.process.persistence.models.PoAttachment;
import com.owner.process.persistence.models.PoDet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PoAttachmentService {
    @Autowired
    PoAttachmentRepository poAttachmentRepository;

    public List<PoAttachment> getByPoNo(String pk0) throws Exception{
        List<PoAttachment> getVal;
        try{
            getVal = poAttachmentRepository.findByPoNo(pk0);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return getVal;
    }

    public List<PoAttachment> getByUKey(String pk0) throws Exception{
        List<PoAttachment> getVal;
        try{
            getVal = poAttachmentRepository.findByUniqueKey(pk0);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return getVal;
    }

    public void save(PoAttachment poAttachment) throws Exception{
        try{
            poAttachmentRepository.save(poAttachment);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public void delete(PoAttachment poAttachment) throws Exception{
        try{
            poAttachmentRepository.delete(poAttachment);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public void deleteall(List<PoAttachment> poAttachment) throws Exception{
        try{
            poAttachmentRepository.deleteAll(poAttachment);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
