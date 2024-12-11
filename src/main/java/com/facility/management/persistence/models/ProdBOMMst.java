package com.facility.management.persistence.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="##plant##PROD_BOM_MST ")
@Getter
@Setter
public class ProdBOMMst {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="ID")
    private int id;
    @Column(name="PLANT")
    private String plant;
    @Column(name="PITEM")
    private String pitem;
    @Column(name="CITEM")
    private String citem;
    @Column(name="QTY")
    private Double qty;
    @Column(name="SEQNUM")
    private String seqNum;
    @Column(name="REMARK1")
    private String remark1;
    @Column(name="REMARK2")
    private String remark2;
    @Column(name="BOMTYPE")
    private String bomType;
    @Column(name="PARENTUOM")
    private String parentuom;
    @Column(name="CHILDUOM")
    private String childUom;
    @Column(name="CRAT")
    private String createAt;
    @Column(name="CRBY")
    private String createBy;
    @Column(name="UPAT")
    private String updateAt;
    @Column(name="UPBY")
    private String updateBy;
}
