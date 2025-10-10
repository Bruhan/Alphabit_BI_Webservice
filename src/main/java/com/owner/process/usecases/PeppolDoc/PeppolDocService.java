package com.owner.process.usecases.PeppolDoc;


import com.owner.process.persistence.models.PEPPOL_DOC_IDS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PeppolDocService {
    @Autowired
    PeppolDocRepository peppolDocRepository;

    public String createPeppolDoc(String DocId, String plant,String crat,String crby) throws Exception {
        Boolean checkNumber = checkDocId(DocId);
        if (checkNumber) {
            PEPPOL_DOC_IDS peppol_doc_ids = new PEPPOL_DOC_IDS();
            peppol_doc_ids.setId(0);
            peppol_doc_ids.setPlant(plant);
            peppol_doc_ids.setDOC_ID(DocId);
            peppol_doc_ids.setCrAt(crat);
            peppol_doc_ids.setCrBy(crby);
            peppolDocRepository.save(peppol_doc_ids);
            return DocId+" Created Successfully ";
        }else {
            return DocId+" Already Exixt ";
        }
    }
    public String updatePeppolDoc(String DocId, String eid, String plant,String crat,String crby) throws Exception {
        PEPPOL_DOC_IDS docval = getDocval(DocId);
        int id = getDocId(DocId);
        if (id != 0) {
            PEPPOL_DOC_IDS peppol_doc_ids = new PEPPOL_DOC_IDS();
            peppol_doc_ids.setId(id);
            peppol_doc_ids.setPlant(plant);
            peppol_doc_ids.setDOC_ID(DocId);
            peppol_doc_ids.setCrAt(docval.getCrAt());
            peppol_doc_ids.setCrBy(docval.getCrBy());
            peppol_doc_ids.setUpAt(crat);
            peppol_doc_ids.setUpBy(crby);
            peppolDocRepository.save(peppol_doc_ids);
            return DocId+" Updated Successfully ";
        }else {
            return DocId+" Failed to update ";
        }
    }

    public void delPeppolDoc(PEPPOL_DOC_IDS val) throws Exception {
        try {
            peppolDocRepository.delete(val);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
    public List<PEPPOL_DOC_IDS> getAllPeppolDoc() throws Exception {
        List<PEPPOL_DOC_IDS> peppol_doc_ids;
        try {
            peppol_doc_ids = peppolDocRepository.findAll();
        } catch (Exception e) {
            throw new Exception(e);
        }
        return peppol_doc_ids;
    }

    private Integer getDocId(String docid) {
        PEPPOL_DOC_IDS pEPPOL_DOC_IDS = peppolDocRepository.findByDoc_id(docid);
        if (pEPPOL_DOC_IDS == null) {
            return 0;
        } else {
            return pEPPOL_DOC_IDS.getId();
        }

    }

    public PEPPOL_DOC_IDS getDocval(String docid) throws Exception {
        PEPPOL_DOC_IDS getVal = null;
        try {
            getVal = peppolDocRepository.findByDoc_id(docid);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return getVal;
    }

    public Boolean checkDocId(String docId) {
        PEPPOL_DOC_IDS pEPPOL_DOC_IDS = peppolDocRepository.findByDoc_id(docId);
        return pEPPOL_DOC_IDS == null;
    }


}