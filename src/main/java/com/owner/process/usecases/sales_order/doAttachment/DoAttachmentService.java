package com.owner.process.usecases.sales_order.doAttachment;

import com.owner.process.persistence.models.DoAttachment;
import com.owner.process.persistence.models.PoAttachment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoAttachmentService {
    @Autowired
    DoAttachmentRepository doAttachmentRepository;

    public List<DoAttachment> getByUKey(String pk0) throws Exception{
        List<DoAttachment> getVal;
        try{
            getVal = doAttachmentRepository.findByUniqueKey(pk0);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return getVal;
    }

    public void deleteall(List<DoAttachment> doAttachment) throws Exception{
        try{
            doAttachmentRepository.deleteAll(doAttachment);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

}
