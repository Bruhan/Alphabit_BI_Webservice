package com.owner.process.persistence.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="##plant##PEPPOL_RECEIVED_DATA")
@Getter
@Setter
public class PEPPOL_RECEIVED_DATA {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="ID")
    private int Id;
    @Column(name="PLANT")
    private String plant;
    @Column(name="EVENT")
    private String event;
    @Column(name="DOCID")
    private String docId;
    @Column(name="RECEIVEDAT")
    private String receiveDat;
    @Column(name="INVOICEFILEURL")
    private String invoiceFileURL;
    @Column(name="EVIDENCEFILEURL")
    private String evidenceFileURL;
    @Column(name="BILLNO")
    private String billNO;
    @Column(name="EXPIRESAT")
    private String expireSAT;
    @Column(name ="BILL_STATUS")
    private int billStatus;
    @Column(name="CRAT")
    private String crAt;
    @Column(name="CRBY")
    private String crBy;
    @Column(name="UPAT")
    private String upAt;
    @Column(name="UPBY")
    private String upBy;
}
