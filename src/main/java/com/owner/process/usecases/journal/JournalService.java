package com.owner.process.usecases.journal;

import com.owner.process.persistence.models.FinJournalDet;
import com.owner.process.persistence.models.FinJournalHdr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JournalService {
    @Autowired
    JournalRepository journalRepository;
    @Autowired
    JournalDetRepository journalDetRepository;

    public int saveFinJournalHdr(FinJournalHdr finJournalHdr) throws Exception {
        int id;
        try {
            id = journalRepository.save(finJournalHdr).getId();
        }catch (Exception e){
            throw new Exception(e);
        }
        return id;
    }

    public void saveJournalDet(FinJournalDet finJournalDet) throws Exception {
        try {
            journalDetRepository.save(finJournalDet);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

}
