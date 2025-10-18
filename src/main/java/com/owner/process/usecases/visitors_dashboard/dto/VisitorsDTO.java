package com.owner.process.usecases.visitors_dashboard.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VisitorsDTO {
    private Long visitorsCount;
    private String visitorsType;
    private Date visitedTime;
    private String emotion;
    private String outlet;
    private String cameraNo;
    private Long conversionRate;
}
