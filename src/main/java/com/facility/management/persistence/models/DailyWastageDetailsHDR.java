package com.facility.management.persistence.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="##plant##DAILY_WASTAGE_DETAILS_HDR")
@Getter
@Setter
@Builder
public class DailyWastageDetailsHDR {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @Column(name = "PLANT")
    private String plant;

    @Column(name = "TOTAL_WASTAGE_TYPE")
    private String totalWastageType;

    @Column(name = "TOTAL_WASTAGE_QTY")
    private double totalWastageQty;

    @Column(name = "TOTAL_WASTAGE_UOM")
    private String totalWastageUOM;

    @Column(name = "PROJECTNO")
    private String projectNo;

    @Column(name = "PROCESSED_QTY")
    private double processedQty;

    @Column(name = "PENDING_QTY")
    private double pendingQty;

    @Column(name="CRAT")
    private String crAt;
    @Column(name="CRBY")
    private String crBy;
    @Column(name="UPAT")
    private String upAt;
    @Column(name="UPBY")
    private String upBy;
}
