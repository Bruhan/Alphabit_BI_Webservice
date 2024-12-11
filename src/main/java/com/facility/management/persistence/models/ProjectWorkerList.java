package com.facility.management.persistence.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigInteger;

@Entity
@Table(name="##plant##PROJECT_WORKERLIST")
@Getter
@Setter
@Builder
public class ProjectWorkerList {
    @Column(name = "PLANT")
    private String plant;

    @Column(name = "ID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "PROJECTNO")
    private String projectNo;

    @Column(name = "EMPID")
    private BigInteger empId;

    @Column(name = "EMPCODE")
    private String empCode;

    @Column(name = "EMPNAME")
    private String empName;

    @Column(name = "PROJECTIN_DATE")
    private String projectInDate;

    @Column(name = "PROJECTOUT_DATE")
    private String projectOutDate;

    @Column(name = "STATUS")
    private Byte status;

    @Column(name="CRAT")
    private String crAt;
    @Column(name="CRBY")
    private String crBy;
    @Column(name="UPAT")
    private String upAt;
    @Column(name="UPBY")
    private String upBy;
}
