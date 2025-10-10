package com.owner.process.usecases.sales_order.doHdrApproval;

import com.owner.process.persistence.models.DoHdrApproval;
import com.owner.process.persistence.models.PoHdrApproval;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DoHderApprovalService {
    @Autowired
    DoHdrApprovalRepositoy doHdrApprovalRepositoy;


    public DoHdrApproval getDoHdrAppByUkey(String pk0) throws Exception{
        DoHdrApproval val;
        try{
            val = doHdrApprovalRepositoy.findByUniqueKey(pk0);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
        return val;
    }

    public void saveDoHdrApp(DoHdrApproval doHdrApproval) throws Exception{
        DoHdrApproval val;
        try{
            doHdrApprovalRepositoy.save(doHdrApproval);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }


}
