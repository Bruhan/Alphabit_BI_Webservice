package com.owner.process.usecases.purchase_order.poHdr;

import com.owner.process.persistence.models.PoHdr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PoHdrService {

    @Autowired
    PoHdrRepository poHdrRepository;

    public List<PoHdr> getPoHdrByApprovalStatus(String pk0) throws Exception {
        List<PoHdr> getVal;
        try {
            getVal = poHdrRepository.findByApprovalSataus(pk0);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return getVal;
    }

    public List<PoHdr> getPoHdrByApprovalStatusPage(int page,int pcount) throws Exception {
        List<PoHdr> getVal;
        try {
            getVal = poHdrRepository.findByApprovalSatausPagination(page,pcount);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return getVal;
    }

    public PoHdr getPoHdrByPoNo(String pk0) throws Exception {
        PoHdr getVal = new PoHdr();
        try {
            List<PoHdr> getVallist = poHdrRepository.findByPurchaseNo(pk0);
            for (PoHdr poHdr:getVallist) {
                getVal = poHdr;
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return getVal;
    }

    public PoHdr getPoHdrByUkey(String pk0) throws Exception {
        PoHdr getVal = new PoHdr();
        try {
            List<PoHdr> getVallist = poHdrRepository.findByUniqueKey(pk0);
            for (PoHdr poHdr:getVallist) {
                getVal = poHdr;
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return getVal;
    }

    public void savePoHdr(PoHdr poHdr) throws Exception {
        try {
            poHdrRepository.save(poHdr);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public void delete(PoHdr poHdr) throws Exception {
        try {
            poHdrRepository.delete(poHdr);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

}
