package com.owner.process.persistence.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigInteger;

@Entity
@Table(name="##plant##PROJECT_WORKEMPLOYEE")
@Getter
@Setter
public class ProjectWorkEmployee {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="ID_EMP")
    private int ID_EMP;
    @Column(name="PLANT")
    private String PLANT;
    @Column(name="PROJECTNO")
    private String PROJECTNO;
    @Column(name="WORKLNNO")
    private int WORKLNNO;
    @Column(name="WORKID")
    private BigInteger WORKID;
    @Column(name="EMPLOYEE")
    private String EMPLOYEE;
    @Column(name="EMP_ID")
    private String EMP_ID;
    @Column(name="CRAT")
    private String CRAT;
    @Column(name="CRBY")
    private String CRBY;
    @Column(name="UPAT")
    private String UPAT;
    @Column(name="UPBY")
    private String UPBY;
}
