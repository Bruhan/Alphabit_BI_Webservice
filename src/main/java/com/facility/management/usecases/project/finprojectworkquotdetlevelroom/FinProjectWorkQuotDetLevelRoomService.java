package com.facility.management.usecases.project.finprojectworkquotdetlevelroom;

import com.facility.management.persistence.models.FinProjectWorkQuotDet;
import com.facility.management.persistence.models.FinProjectWorkQuotDetLevelRoom;
import com.facility.management.usecases.project.projectpojo.FinProjectWorkQuotDetLevelRoomPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FinProjectWorkQuotDetLevelRoomService {
    @Autowired
    FinProjectWorkQuotDetLevelRoomRepository finProjectWorkQuotDetLevelRoomRepository;

    public List<FinProjectWorkQuotDetLevelRoom> getFinProjectWorkQuotDetLevelRoombyWkey(String wkey) throws Exception {
        List<FinProjectWorkQuotDetLevelRoom> getVal = new ArrayList<FinProjectWorkQuotDetLevelRoom>();
        try {
            getVal=finProjectWorkQuotDetLevelRoomRepository.getFinProjectWorkQuotDetLevelRoombyWkey(wkey);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return getVal;
    }

    public List<FinProjectWorkQuotDetLevelRoomPojo> getProWQDetLevelRoombyWkeypojo(String wkey) throws Exception {
        List<FinProjectWorkQuotDetLevelRoomPojo> getVal = new ArrayList<FinProjectWorkQuotDetLevelRoomPojo>();
        try {
            getVal=finProjectWorkQuotDetLevelRoomRepository.getProWQDetLevelRoombyWkeypojo(wkey);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return getVal;
    }

}
