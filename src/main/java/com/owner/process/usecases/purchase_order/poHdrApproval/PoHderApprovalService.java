package com.owner.process.usecases.purchase_order.poHdrApproval;

import com.owner.process.persistence.models.PoHdrApproval;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PoHderApprovalService {
    @Autowired
    PoHdrApprovalRepositoy poHdrApprovalRepositoy;

    public PoHdrApproval getPoHdrAppByUkey(String pk0) throws Exception{
        PoHdrApproval val;
        try{
            val = poHdrApprovalRepositoy.findByUniqueKey(pk0);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
        return val;
    }

    public void savePoHdrApp(PoHdrApproval poHdrApproval) throws Exception{
        PoHdrApproval val;
        try{
            poHdrApprovalRepositoy.save(poHdrApproval);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
}
