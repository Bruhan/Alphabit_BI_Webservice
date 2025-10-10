package com.owner.process.usecases.PeppolReceivedData.dao;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PeppolReceivedDataDao {
    private String plant;
    private String DocId;
    private String event;
    private String receiveDat;
    private String invoiceFileURL;
    private String evidenceFileURL;
    private String billNO;
    private String crAt;
    private String crBy;
    private String upAt;
    private String upBy;
    private String expireSAT;
    private int billStatus;
    private int ID;
    }
