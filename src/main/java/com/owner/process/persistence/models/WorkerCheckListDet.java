package com.owner.process.persistence.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="##plant##WORKERCHECKLISTDET")
@Getter
@Setter
public class WorkerCheckListDet {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="ID")
    private int ID;
    @Column(name="PLANT")
    private String PLANT;
    @Column(name="WORKERCHECKLIST")
    private String WORKERCHECKLIST;
    @Column(name="HDRID")
    private int hdrid;
    @Column(name="CHECKLISTSTATUS")
    private Short checkListStatus;
    @Column(name="CRAT")
    private String CRAT;
    @Column(name="CRBY")
    private String CRBY;
    @Column(name="UPAT")
    private String UPAT;
    @Column(name="UPBY")
    private String UPBY;
}
