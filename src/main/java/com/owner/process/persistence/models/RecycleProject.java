package com.owner.process.persistence.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="##plant##FINRECYCLEPROJECT")
@Getter
@Setter
public class RecycleProject {
    @Column(name="PLANT")
    private String plant;
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="ID")
    private int id;
    @Column(name="CUSTNO")
    private String custNo;
    @Column(name="PROJECT")
    private String project;
    @Column(name="PROJECT_NAME")
    private String projectName;
    @Column(name="LOC")
    private String loc;
}
