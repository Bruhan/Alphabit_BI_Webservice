package com.owner.process.persistence.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "##plant##PROJECTFLOORROOMS")
public class ProjectFloorRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name="PLANT")
    private String plant;
    @Column(name="FLOOR_ID")
    private Integer floorId;
    @Column(name="ROOM_NAME")
    private String roomName;
    @Column(name="CRAT")
    private String crat;
    @Column(name="CRBY")
    private String crby;
    @Column(name="UPAT")
    private String upat;
    @Column(name="UPBY")
    private String upby;

}

