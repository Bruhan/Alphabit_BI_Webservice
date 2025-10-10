package com.owner.process.persistence.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="##plant##PROJECTSTOCKREQUESTDET")
@Getter
@Setter
public class ProjectStockRequestDET {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="ID")
    private int ID;
    @Column(name="PLANT")
    private String PLANT;
    @Column(name="HDRID")
    private int HDRID;
    @Column(name="LNSTATUS")
    private String LNSTATUS;
    @Column(name="LNNO")
    private int LNNO;
    @Column(name="ITEM")
    private String ITEM;
    @Column(name="UOM")
    private String UOM;
    @Column(name="QTY")
    private Float QTY;
    @Column(name="PROCESSEDQTY")
    private Float PROCESSEDQTY;
    @Column(name="BALANCEQTY")
    private Float BALANCEQTY;
    @Column(name="CRAT")
    private String CRAT;
    @Column(name="CRBY")
    private String CRBY;
    @Column(name="UPAT")
    private String UPAT;
    @Column(name="UPBY")
    private String UPBY;
}
