package com.facility.management.persistence.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigInteger;

@Entity
@Table(name="##plant##FINPROJECT_WORKDET")
@Getter
@Setter
public class FinProjectWorkDet {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="ID")
    private int ID;
    @Column(name="PLANT")
    private String PLANT;
    @Column(name="LNNO")
    private int LNNO;
    @Column(name="PROJECTHDRID")
    private int PROJECTHDRID;
    @Column(name="WORKID")
    private BigInteger WORKID;
    @Column(name="WORKTYPE_ID")
    private BigInteger WORKTYPE_ID;
    @Column(name="QTY")
    private Float QTY;
    @Column(name="UOM")
    private String UOM;
    @Column(name="PQTY")
    private Float PQTY;
    @Column(name="PUOM")
    private String PUOM;
    @Column(name="MONTHS")
    private Float MONTHS;
    @Column(name="DAYS")
    private Float DAYS;
    @Column(name="TOTALDAYS")
    private Float TOTALDAYS;
    @Column(name="UNITRATE")
    private Float UNITRATE;
    @Column(name="TOTALAMOUNT")
    private Float TOTALAMOUNT;
    @Column(name="CURRENCYUSEQT")
    private Float CURRENCYUSEQT;
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
}
