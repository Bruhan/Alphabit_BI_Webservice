package com.owner.process.usecases.visitors_dashboard.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SaveVisitorsDTO {
    private String plant;
    private Long visitorsCount;
    private String visitorsType;
    private Date visitedTime;
    private String outlet;
    private String cameraNo;
    private Long conversionRate;
}
