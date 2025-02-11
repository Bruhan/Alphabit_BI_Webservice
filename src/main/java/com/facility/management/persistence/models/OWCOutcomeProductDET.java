package com.facility.management.persistence.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name="##plant##OWCOUTCOMEPRODUCTDET")
@Builder
public class OWCOutcomeProductDET {
    @Column(name = "PLANT")
    private String plant;

    @Column(name = "ID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "PROJECTNO")
    private String projectNo;

    @Column(name = "PRODUCT")
    private String product;

    @Column(name = "QTY")
    private double qty;

    @Column(name = "UOM")
    private String uom;

    @Column(name = "PROCESSED_QTY")
    private double processedQty;

    @Column(name = "PENDING_QTY")
    private double pendingQty;

    @Column(name="CRAT")
    private String crAt;
    @Column(name="CRBY")
    private String crBy;
    @Column(name="UPAT")
    private String upAt;
    @Column(name="UPBY")
    private String upBy;
}
