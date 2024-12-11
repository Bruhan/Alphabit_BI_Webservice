package com.facility.management.persistence.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="##plant##FINPROJECT_ITEMDET")
@Getter
@Setter
public class FinProjectItem {
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
    @Column(name="ITEM")
    private String ITEM;
    @Column(name="ACCOUNT_NAME")
    private String ACCOUNT_NAME;
    @Column(name="QTY")
    private Float QTY;
    @Column(name="UOM")
    private String UOM;
    @Column(name="UNITPRICE")
    private Float UNITPRICE;
    @Column(name="DISCOUNT")
    private Float DISCOUNT;
    @Column(name="DISCOUNT_TYPE")
    private String DISCOUNT_TYPE;
    @Column(name="TAX_TYPE")
    private String TAX_TYPE;
    @Column(name="CURRENCYUSEQT")
    private Float CURRENCYUSEQT;
    @Column(name="AMOUNT")
    private Float AMOUNT;
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
