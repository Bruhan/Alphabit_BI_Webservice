package com.facility.management.persistence.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="##plant##DAILY_WASTAGE_DETAILS_DET")
@Getter
@Setter
@Builder
public class DailyWastageDetailsDET {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @Column(name = "PLANT")
    private String plant;

    @Column(name = "PROJECTNO")
    private String projectNo;

    @Column(name = "WASTAGE_TYPE")
    private String wastageType;

    @Column(name = "WASTAGE_QTY")
    private double wastageQty;

    @Column(name = "WASTAGE_UOM")
    private String wastageUOM;

    @Column(name = "SUPERVISOR_CODE")
    private String supervisorCode;

    @Column(name = "DATE")
    private String date;

    @Column(name="CRAT")
    private String crAt;
    @Column(name="CRBY")
    private String crBy;
    @Column(name="UPAT")
    private String upAt;
    @Column(name="UPBY")
    private String upBy;
}
