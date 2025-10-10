package com.owner.process.usecases.PeppolReceivedData;


import com.owner.process.persistence.models.PEPPOL_RECEIVED_DATA;
import com.owner.process.usecases.PeppolReceivedData.dao.PeppolReceivedDataDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PeppolReceivedDataService {
    @Autowired
    PeppolReceivedDataRepository peppolReceivedDataRepository;

    public String createPeppolReceivedData(PeppolReceivedDataDao peppolReceivedDataDao, String plant, String crat, String crby) throws Exception {
        Boolean checkNumber = checkDocId(peppolReceivedDataDao.getDocId());
        if (checkNumber) {
            PEPPOL_RECEIVED_DATA peppol_received_data = new PEPPOL_RECEIVED_DATA();
            peppol_received_data.setPlant(plant);
            peppol_received_data.setId(0);
            peppol_received_data.setDocId(peppolReceivedDataDao.getDocId());
            peppol_received_data.setEvent(peppolReceivedDataDao.getEvent());
            peppol_received_data.setReceiveDat(peppolReceivedDataDao.getReceiveDat());
            peppol_received_data.setInvoiceFileURL(peppolReceivedDataDao.getInvoiceFileURL());
            peppol_received_data.setEvidenceFileURL(peppolReceivedDataDao.getEvidenceFileURL());
            peppol_received_data.setBillNO(peppolReceivedDataDao.getBillNO());
            peppol_received_data.setExpireSAT(peppolReceivedDataDao.getExpireSAT());
            peppol_received_data.setBillStatus(peppolReceivedDataDao.getBillStatus());
            peppol_received_data.setCrAt(crat);
            peppol_received_data.setCrBy(crby);
            peppolReceivedDataRepository.save(peppol_received_data);
            return "Created Successfully ";
        }else {
            return "Already Exixt ";
        }
    }
    public String updatePeppolreceivedData(PeppolReceivedDataDao peppolReceivedDataDao, String eid, String plant,String crat,String crby) throws Exception {

        PEPPOL_RECEIVED_DATA docval = getDocval(peppolReceivedDataDao.getDocId());
        int id = getDocId(peppolReceivedDataDao.getDocId());
        if (id != 0) {
            PEPPOL_RECEIVED_DATA peppol_received_data = new PEPPOL_RECEIVED_DATA();
            peppol_received_data.setId(id);
            peppol_received_data.setPlant(plant);
            peppol_received_data.setDocId(peppolReceivedDataDao.getDocId());
            peppol_received_data.setEvent(peppolReceivedDataDao.getEvent());
            peppol_received_data.setReceiveDat(peppolReceivedDataDao.getReceiveDat());
            peppol_received_data.setInvoiceFileURL(peppolReceivedDataDao.getInvoiceFileURL());
            peppol_received_data.setEvidenceFileURL(peppolReceivedDataDao.getEvidenceFileURL());
            peppol_received_data.setBillNO(peppolReceivedDataDao.getBillNO());
            peppol_received_data.setExpireSAT(peppolReceivedDataDao.getExpireSAT());
            peppol_received_data.setBillStatus(peppolReceivedDataDao.getBillStatus());
            peppol_received_data.setCrAt(docval.getCrAt());
            peppol_received_data.setCrBy(docval.getCrBy());
            peppol_received_data.setUpAt(crat);
            peppol_received_data.setUpBy(crby);
            peppolReceivedDataRepository.save(peppol_received_data);
            return "Updated Successfully ";
        }else {
            return "Failed to update ";
        }
    }

    public void delPeppolreceivedData(PEPPOL_RECEIVED_DATA val) throws Exception {
        try {
            peppolReceivedDataRepository.delete(val);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
    public List<PEPPOL_RECEIVED_DATA> getAllpeppolReceivedData() throws Exception {
        List<PEPPOL_RECEIVED_DATA> peppol_received_data;
        try {
            peppol_received_data = peppolReceivedDataRepository.findAll();
        } catch (Exception e) {
            throw new Exception(e);
        }
        return peppol_received_data;
    }

    private Integer getDocId(String docid) {
        PEPPOL_RECEIVED_DATA pEPPOL_RECEIVED_DATA = peppolReceivedDataRepository.findByDocId(docid);
        if (pEPPOL_RECEIVED_DATA == null) {
            return 0;
        } else {
            return pEPPOL_RECEIVED_DATA.getId();
        }

    }
    private Integer getDocId(String docId,String event,String rec,String bill) {
        PEPPOL_RECEIVED_DATA pEPPOL_RECEIVED_DATA = peppolReceivedDataRepository.findByDoc_id(docId,event,rec,bill);
        if (pEPPOL_RECEIVED_DATA == null) {
            return 0;
        } else {
            return pEPPOL_RECEIVED_DATA.getId();
        }

    }

    public PEPPOL_RECEIVED_DATA getDocval(String docid) throws Exception {
        PEPPOL_RECEIVED_DATA getVal = null;
        try {
            getVal = peppolReceivedDataRepository.findByDocId(docid);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return getVal;
    }
    public PEPPOL_RECEIVED_DATA getDocval(String docId,String event,String rec,String bill) throws Exception {
        PEPPOL_RECEIVED_DATA getVal = null;
        try {
            getVal = peppolReceivedDataRepository.findByDoc_id(docId,event,rec,bill);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return getVal;
    }

    public Boolean checkDocId(String docId) {
        PEPPOL_RECEIVED_DATA pEPPOL_RECEIVED_DATA = peppolReceivedDataRepository.findByDocId(docId);
        return pEPPOL_RECEIVED_DATA == null;
    }
    public Boolean checkDocId(String docId,String event,String rec,String bill) {
        PEPPOL_RECEIVED_DATA pEPPOL_RECEIVED_DATA = peppolReceivedDataRepository.findByDoc_id(docId,event,rec,bill);
        return pEPPOL_RECEIVED_DATA == null;
    }


}