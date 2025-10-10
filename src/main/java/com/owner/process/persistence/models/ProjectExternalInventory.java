package com.owner.process.persistence.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name="##plant##PROJECT_EXTERNAL_INVENTORY")
@Builder
public class ProjectExternalInventory {
    @Column(name = "PLANT")
    private String plant;

    @Column(name = "ID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "PROJECTNO")
    private String projectNo;

    @Column(name = "DATE")
    private String date;

    @Column(name="QTY")
    private double qty;

    @Column(name = "PRODUCT")
    private String product;

    @Column(name="UOM")
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
