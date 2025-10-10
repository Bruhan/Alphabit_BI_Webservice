package com.owner.process.persistence.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name="##plant##BIOGASDET")
@Builder
public class BioGasDET {
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

    @Column(name = "QTY")
    private double qty;

    @Column(name = "UOM")
    private String uom;

    @Column(name = "EMPCODE")
    private String empCode;

    @Column(name="CRAT")
    private String crAt;
    @Column(name="CRBY")
    private String crBy;
    @Column(name="UPAT")
    private String upAt;
    @Column(name="UPBY")
    private String upBy;
}
