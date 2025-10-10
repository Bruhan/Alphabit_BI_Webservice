package com.owner.process.usecases.project.finworkquotdet;

import com.owner.process.persistence.models.FinProjectWorkQuotDet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FinProjectWorkQuotDetService {
    @Autowired
    FinProjectWorkQuotDetRepository finProjectWorkQuotDetRepository;


    public List<FinProjectWorkQuotDet> getFinProjectWorkQuotDetbyProject(int pid) throws Exception {
        List<FinProjectWorkQuotDet> getVal = new ArrayList<FinProjectWorkQuotDet>();
        try {
            getVal=finProjectWorkQuotDetRepository.getFinProjectWorkQuotDetbyProject(pid);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return getVal;
    }

    public FinProjectWorkQuotDet getFinProjectWorkQuotDetbyid(int id) throws Exception {
        FinProjectWorkQuotDet getVal = new FinProjectWorkQuotDet();
        try {
            getVal=finProjectWorkQuotDetRepository.getFinProjectWorkQuotDetbyid(id);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return getVal;
    }

    public Integer getWorktypeidbyid(int id) throws Exception {
        Integer getVal = 0;
        try {
            getVal=finProjectWorkQuotDetRepository.getWorktypeidbyid(id);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return getVal;
    }
}
