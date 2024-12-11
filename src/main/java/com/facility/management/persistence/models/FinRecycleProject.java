package com.facility.management.persistence.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigInteger;

@Entity
@Table(name="##plant##FINRECYCLEPROJECT")
@Getter
@Setter
@Builder
public class FinRecycleProject {

    @Column(name = "PLANT")
    private String plant;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="ID")
    private int id;

    @Column(name = "CUSTNO")
    private String custNo;

    @Column(name = "PROJECT")
    private String project;

    @Column(name = "PROJECT_NAME")
    private String projectName;

    @Column(name = "PROJECT_DATE")
    private String projectDate;

    @Column(name="PROJECT_STATUS")
    private String projectStatus;

    @Column(name="REFERENCE")
    private String reference;

    @Column(name="ESTIMATE_COST")
    private String estimateCost;

    @Column(name="ESTIMATE_TIME")
    private String estimateTime;

    @Column(name="BILLING_OPTION")
    private String billingOption;

    @Column(name="ISPERHOURWAGES")
    private Byte isPerHourWages;

    @Column(name="PERHOURWAGESCOST")
    private double perHourWagesCost;

    @Column(name="NOTE")
    private String note;

    @Column(name="CRAT")
    private String CRAT;

    @Column(name="CRBY")
    private String CRBY;

    @Column(name="UPAT")
    private String UPAT;

    @Column(name="UPBY")
    private String UPBY;

    @Column(name="MANDAY_HOUR")
    private double mandayHour;

    @Column(name = "ISMANDAY_HOUR")
    private Byte isMandayHour;

    @Column(name = "PROJECTTYPE_ID")
    private BigInteger projectTypeId;

    @Column(name="PROJECTCLASSIFICATION_ID")
    private BigInteger projectClassificationId;

    @Column(name="EXPIRY_DATE")
    private String expiryDate;

    @Column(name="QUOTATION")
    private String quotation;

    @Column(name="PREPARATION")
    private String preparation;

    @Column(name="MANAGERENG")
    private String managerEng;

    @Column(name = "SUPERVISOR")
    private String supervisor;

    @Column(name = "SAFETYSUPERVISOR")
    private String safetySupervisor;

    @Column(name="QUOTATION_DATE")
    private String quotationDate;

    @Column(name="CURRENCYID")
    private String currencyId;

    @Column(name = "CURRENCYUSEQT")
    private double currencyUseQt;

    @Column(name = "TOTAL_AMOUNT")
    private double totalAmount;

    @Column(name = "LOC")
    private String loc;

    @Column(name = "TOTAL_WORKAMOUNT")
    private double totalWorkAmount;

    @Column(name = "SCOPE")
    private String scope;

    @Column(name = "BYDATE")
    private Byte byDate;

    @Column(name = "PAYTERMS")
    private String payTerms;

    @Column(name = "SCOPEQTY")
    private String scopeQty;

    @Column(name = "SCOPEAMOUNT")
    private double scopeAmount;

    @Column(name = "SCOPEDISCOUNT")
    private double scopeDiscount;

    @Column(name = "TOTAL_WORKQUOTAMOUNT")
    private double totalWorkQuotAmount;


}
