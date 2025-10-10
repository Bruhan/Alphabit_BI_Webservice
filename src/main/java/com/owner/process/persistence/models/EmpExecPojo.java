package com.owner.process.persistence.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="##plant##EMP_MST")
@Getter
@Setter
public class EmpExecPojo {
    @Column(name="PLANT")
    private String plant;
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="ID")
    private int id;
    @Column(name="EMPNO")
    private String empNo;
    @Column(name="FNAME")
    private String fName;
}
