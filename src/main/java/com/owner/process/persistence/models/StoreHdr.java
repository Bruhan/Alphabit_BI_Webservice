package com.owner.process.persistence.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="##plant##STOREITEMHDR ")
@Getter
@Setter
public class StoreHdr {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="ID")
    private int id;
    @Column(name="PLANT")
    private String plant;
    @Column(name="ITEM")
    private String item;
    @Column(name="ITEM_DESC")
    private String itemDesc;
    @Column(name="UOM")
    private String uom;
    @Column(name="ORDER_QTY")
    private Double orderQty;
    @Column(name="PROCESSED_QTY")
    private Double processedQty;
    @Column(name="BALANCE_QTY")
    private Double balanceQty;
    @Column(name="DEPARTMENT")
    private String department;
    @Column(name="STATUS")
    private String status;
    @Column(name="CRAT")
    private String createAt;
    @Column(name="CRBY")
    private String createBy;
    @Column(name="UPAT")
    private String updateAt;
    @Column(name="UPBY")
    private String updateBy;

}
