package com.facility.management.persistence.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="##plant##PROJECTWORKALLOCATIONDET")
@Getter
@Setter
public class ProjectWorkAllocationDET {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="ID")
    private int ID;
    @Column(name="PLANT")
    private String PLANT;
    @Column(name="WORKDATE")
    private String WORKDATE;
    @Column(name="HDRID")
    private int HDRID;
    @Column(name="EMP_ID")
    private String EMP_ID;
    @Column(name="EMPLOYEE")
    private String EMPLOYEE;
    @Column(name="ISWORKUPDATER")
    private Short ISWORKUPDATER;
    @Column(name="ISWORKUPDATED")
    private Short ISWORKUPDATED;
    @Column(name="CRAT")
    private String CRAT;
    @Column(name="CRBY")
    private String CRBY;
    @Column(name="UPAT")
    private String UPAT;
    @Column(name="UPBY")
    private String UPBY;
}
