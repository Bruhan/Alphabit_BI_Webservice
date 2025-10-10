package com.owner.process.persistence.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="##plant##LOCMST")
@Getter
@Setter
public class LocationMst {
    @Column(name="PLANT")
    private String plant;
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="ID")
    private int id;
    @Column(name="LOC")
    private String loc;
    @Column(name="LOCDESC")
    private String locDescription;
}
