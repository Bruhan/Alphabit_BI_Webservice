package com.owner.process.usecases.project.finprojetworkdet;

import com.owner.process.persistence.models.FinProjectWorkDet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FinProjectWorkDetService {
    @Autowired
    FinProjectWorkDetRepository finProjectWorkDetRepository;

    public List<FinProjectWorkDet> getFinProjectWorkDetbyProject(int pid) throws Exception {
        List<FinProjectWorkDet> getVal = new ArrayList<FinProjectWorkDet>();
        try {
            getVal=finProjectWorkDetRepository.getFinProjectWorkDetbyProject(pid);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return getVal;
    }
}
