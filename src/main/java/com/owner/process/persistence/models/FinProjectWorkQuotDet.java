package com.owner.process.persistence.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigInteger;

@Entity
@Table(name="##plant##FINPROJECT_WORKQUOTDET")
@Getter
@Setter
public class FinProjectWorkQuotDet {
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
    @Column(name="WORKTYPE_ID")
    private BigInteger WORKTYPE_ID;
    @Column(name="WORKTYPE_DESC")
    private String WORKTYPE_DESC;
    @Column(name="ITEM_CONSIDERATION")
    private String ITEM_CONSIDERATION;
    @Column(name="UOM")
    private String UOM;
    @Column(name="QTY")
    private Float QTY;
    @Column(name="WKEY")
    private String WKEY;
    @Column(name="MATERIAL_UNITRATE")
    private Float MATERIAL_UNITRATE;
    @Column(name="MATERIAL_AMOUNT")
    private Float MATERIAL_AMOUNT;
    @Column(name="INSTALL_UNITRATE")
    private Float INSTALL_UNITRATE;
    @Column(name="INSTALL_AMOUNT")
    private Float INSTALL_AMOUNT;
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
