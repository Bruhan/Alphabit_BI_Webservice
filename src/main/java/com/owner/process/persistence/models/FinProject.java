package com.owner.process.persistence.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigInteger;

@Entity
@Table(name="##plant##FINPROJECT")
@Getter
@Setter
public class FinProject {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="ID")
    private int ID;
    @Column(name="PLANT")
    private String PLANT;
    @Column(name="CUSTNO")
    private String CUSTNO;
    @Column(name="PROJECT")
    private String PROJECT;
    @Column(name="PROJECT_NAME")
    private String PROJECT_NAME;
    @Column(name="PROJECT_DATE")
    private String PROJECT_DATE;
    @Column(name="PROJECT_STATUS")
    private String PROJECT_STATUS;
    @Column(name="REFERENCE")
    private String REFERENCE;
    @Column(name="ESTIMATE_COST")
    private String ESTIMATE_COST;
    @Column(name="ESTIMATE_TIME")
    private String ESTIMATE_TIME;
    @Column(name="BILLING_OPTION")
    private String BILLING_OPTION;
    @Column(name="ISPERHOURWAGES")
    private Short ISPERHOURWAGES;
    @Column(name="PERHOURWAGESCOST")
    private Float PERHOURWAGESCOST;
    @Column(name="PROJECTTYPE_ID")
    private BigInteger PROJECTTYPE_ID;
    @Column(name="PROJECTCLASSIFICATION_ID")
    private BigInteger PROJECTCLASSIFICATION_ID;
    @Column(name="NOTE")
    private String NOTE;
    @Column(name="CRAT")
    private String CRAT;
    @Column(name="CRBY")
    private String CRBY;
    @Column(name="UPAT")
    private String UPAT;
    @Column(name="UPBY")
    private String UPBY;
    @Column(name="MANDAY_HOUR")
    private Float MANDAY_HOUR;
    @Column(name="ISMANDAY_HOUR")
    private Short ISMANDAY_HOUR;
    @Column(name="CURRENCYID")
    private String CURRENCYID;
    @Column(name="CURRENCYUSEQT")
    private Float CURRENCYUSEQT;
    @Column(name="TOTAL_AMOUNT")
    private Float TOTAL_AMOUNT;
    @Column(name="LOC")
    private String LOC;
    @Column(name="TOTAL_WORKAMOUNT")
    private Float TOTAL_WORKAMOUNT;
    @Column(name="SCOPE")
    private String SCOPE;
    @Column(name="BYDATE")
    private Short BYDATE;
    @Column(name="PAYTERMS")
    private String PAYTERMS;
    @Column(name="SCOPEQTY")
    private String SCOPEQTY;
    @Column(name="SCOPEAMOUNT")
    private Float SCOPEAMOUNT;
    @Column(name="SCOPEDISCOUNT")
    private Float SCOPEDISCOUNT;
    @Column(name="TOTAL_WORKQUOTAMOUNT")
    private Float TOTAL_WORKQUOTAMOUNT;
    @Column(name="EXPIRY_DATE")
    private String EXPIRY_DATE;
    @Column(name="QUOTATION")
    private String QUOTATION;
    @Column(name="MANAGERENG")
    private String MANAGERENG;
    @Column(name="SUPERVISOR")
    private String SUPERVISOR;
    @Column(name="SAFETYSUPERVISOR")
    private String SAFETYSUPERVISOR;
    @Column(name="QUOTATION_DATE")
    private String QUOTATION_DATE;
}
