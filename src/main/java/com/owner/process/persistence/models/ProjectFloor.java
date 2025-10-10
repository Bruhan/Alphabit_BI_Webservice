package com.owner.process.persistence.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "##plant##PROJECTFLOOR")
@Getter
@Setter
public class ProjectFloor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name="PLANT")
    private String plant;
    @Column(name="FLOOR_NAME")
    private String floorName;
    @Column(name="CRAT")
    private String crat;
    @Column(name="CRBY")
    private String crby;
    @Column(name="UPAT")
    private String upat;
    @Column(name="UPBY")
    private String upby;

}