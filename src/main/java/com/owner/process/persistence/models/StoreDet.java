package com.owner.process.persistence.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="##plant##STOREITEMDET ")
@Getter
@Setter
public class StoreDet {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="ID")
    private Integer id;
    @Column(name="PLANT")
    private String plant;
    @Column(name="HDRID")
    private int hdrId;
    @Column(name="DONO")
    private String dono;
    @Column(name="IS_CHILD_ITEM")
    private Short isChildItem;
    @Column(name="PITEM")
    private String pItem;
    @Column(name="PITEM_DESC")
    private String pItemDesc;
    @Column(name="ITEM")
    private String item;
    @Column(name="ITEM_DESC")
    private String itemDesc;
    @Column(name="UOM")
    private String uom;
    @Column(name="ORDER_QTY")
    private Double orderQty;
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
