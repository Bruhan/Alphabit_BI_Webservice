package com.owner.process.persistence.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="##plant##POSOUTLETSTERMINALS")
@Getter
@Setter
public class POSOutletsTerminals {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="ID")
    private int ID;
    @Column(name="PLANT")
    private String PLANT;
    @Column(name="OUTLET")
    private String OUTLET;
    @Column(name="TERMINAL")
    private String TERMINAL;
    @Column(name="TERMINAL_NAME")
    private String TERMINAL_NAME;
    @Column(name="IsActive")
    private String IsActive;
    @Column(name="DEVICE_STATUS")
    private Short DEVICE_STATUS;
    @Column(name="DEVICE_NAME ")
    private String DEVICE_NAME ;
    @Column(name="FLOATAMOUNT")
    private Float FLOATAMOUNT;
    @Column(name="CRAT")
    private String CRAT;
    @Column(name="CRBY")
    private String CRBY;
    @Column(name="UPAT")
    private String UPAT;
    @Column(name="UPBY")
    private String UPBY;
    @Column(name="isAllowVariant")
    private Short ISALLOWVARIANT;
    @Column(name="allowVariant")
    private Float ALLOWVARIANT;
}

