package com.facility.management.usecases.project.projectpojo;

import com.facility.management.persistence.models.FinProjectWorkQuotDet;
import com.facility.management.persistence.models.FinProjectWorkQuotDetLevelRoom;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FinProjectWorkQuotDetPojo {
    private FinProjectWorkQuotDet finProjectWorkQuotDet;
    private List<FinProjectWorkQuotDetLevelRoomPojo> workLevelRoomList;
}
