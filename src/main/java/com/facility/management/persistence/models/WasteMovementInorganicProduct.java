package com.facility.management.persistence.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="##plant##WASTE_MOVEMENT_INORGANIC_PRODUCT")
@Getter
@Setter
public class WasteMovementInorganicProduct {
    @Column(name = "PLANT")
    private String plant;

    @Column(name = "ID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "PROJECTNO")
    private String projectNo;

    @Column(name = "DETID")
    private String detId;

    @Column(name = "HDRID")
    private String hdrId;

    @Column(name = "ITEM")
    private String item;

    @Column(name = "QTY")
    private double qty;

    @Column(name = "UOM")
    private String uom;

    @Column(name="CRAT")
    private String crAt;

    @Column(name="CRBY")
    private String crBy;

    @Column(name="UPAT")
    private String upAt;

    @Column(name="UPBY")
    private String upBy;
}
