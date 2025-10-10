package com.owner.process.usecases.project.projectpojo;

import com.owner.process.persistence.models.FinProjectWorkQuotDet;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FinProjectWorkQuotDetPojo {
    private FinProjectWorkQuotDet finProjectWorkQuotDet;
    private List<FinProjectWorkQuotDetLevelRoomPojo> workLevelRoomList;
}
