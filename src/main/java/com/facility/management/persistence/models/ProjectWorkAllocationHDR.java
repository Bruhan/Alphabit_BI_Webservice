package com.facility.management.persistence.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="##plant##PROJECTWORKALLOCATIONHDR")
@Getter
@Setter
public class ProjectWorkAllocationHDR {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="ID")
    private int ID;
    @Column(name="PLANT")
    private String PLANT;
    @Column(name="PROJECTID")
    private int PROJECTID;
    @Column(name="WORKTYPECATEGORYID")
    private int WORKTYPECATEGORYID;
    @Column(name="PROJECTNO")
    private String PROJECTNO;
    @Column(name="WORKDATE")
    private String WORKDATE;
    @Column(name="WORKQUOTDETID")
    private int WORKQUOTDETID;
    @Column(name="WORKTYPE_DESC")
    private String WORKTYPE_DESC;
    @Column(name="REMARKS")
    private String REMARKS;
    @Column(name="UOM")
    private String UOM;
    @Column(name="QTY")
    private Float QTY;
    @Column(name="FLOOR_ID")
    private int FLOOR_ID;
    @Column(name="ROOM_ID")
    private int ROOM_ID;
    @Column(name="ISCOMPLED")
    private Short ISCOMPLED;
    @Column(name="WORKSTATUS")
    private String WORKSTATUS;
    @Column(name="CRAT")
    private String CRAT;
    @Column(name="CRBY")
    private String CRBY;
    @Column(name="UPAT")
    private String UPAT;
    @Column(name="UPBY")
    private String UPBY;
}
