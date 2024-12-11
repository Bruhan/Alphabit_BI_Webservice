package com.facility.management.persistence.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="##plant##WASTE_MOVEMENT_DET")
@Getter
@Setter
public class WasteMovementDET {
    @Column(name = "PLANT")
    private String plant;

    @Column(name = "ID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "PROJECTNO")
    private String projectNo;

    @Column(name = "CUSTOMERID")
    private String customerId;

    @Column(name = "CUSTOMERNAME")
    private String customerName;

    @Column(name = "HDRID")
    private String hdrId;

    @Column(name = "DESTINATION")
    private String destination;

    @Column(name = "DESTINATION_TYPE")
    private String destinationType;

    @Column(name = "WASTAGE_TYPE")
    private String wastageType;

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
