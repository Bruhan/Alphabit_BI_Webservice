package com.owner.process.persistence.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name="##plant##INORGANIC_WASTE_HDR")
@Builder
public class InorganicWasteHDR {
    @Column(name = "PLANT")
    private String plant;

    @Column(name = "ID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "PROJECTNO")
    private String projectNo;

    @Column(name = "TOTAL_QTY")
    private double totalQty;

    @Column(name = "TOTAL_UOM")
    private String totalUOM;

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
