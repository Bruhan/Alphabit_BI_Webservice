package com.facility.management.persistence.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="##plant##FINPROJECT_MULTISUPERVISOR")
@Getter
@Setter
@Builder
public class FinProjectMultiSupervisor {

    @Column(name = "PLANT")
    private String plant;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="ID")
    private int id;

    @Column(name = "PROJECTHDRID")
    private int projectHdrId;

    @Column(name = "SUPERVISORID")
    private String supervisorId;

    @Column(name = "SUPERVISOR")
    private String supervisor;

    @Column(name = "ISADMIN")
    private Byte isAdmin;

    @Column(name="CRAT")
    private String CRAT;

    @Column(name="CRBY")
    private String CRBY;

    @Column(name="UPAT")
    private String UPAT;

    @Column(name="UPBY")
    private String UPBY;
}
