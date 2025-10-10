package com.owner.process.persistence.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="PEPPOL_DOC_IDS")
@Getter
@Setter
public class PEPPOL_DOC_IDS {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="ID")
    private int Id;
    @Column(name="PLANT")
    private String plant;
    @Column(name="DOC_ID")
    private String DOC_ID;
    @Column(name="CRAT")
    private String crAt;
    @Column(name="CRBY")
    private String crBy;
    @Column(name="UPAT")
    private String upAt;
    @Column(name="UPBY")
    private String upBy;

}
