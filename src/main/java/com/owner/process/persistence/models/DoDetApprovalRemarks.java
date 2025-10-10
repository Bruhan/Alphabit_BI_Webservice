package com.owner.process.persistence.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="##plant##DODETAPPROVAL_REMARKS")
@Getter
@Setter
@IdClass(DoDetApprovalRemarksIds.class)
public class DoDetApprovalRemarks {

    @Column(name="PLANT")
    private String plant;
    @Column(name="ID_REMARKS")
    private int idRemarks;
    @Column(name="DONO")
    private String doNo;
    @Id
    @Column(name="DOLNNO")
    private int doLineNo;
    @Column(name="ITEM")
    private String item;
    @Column(name="REMARKS")
    private String reMarks;
    @Column(name="CRAT")
    private String crAt;
    @Column(name="CRBY")
    private String crBy;
    @Column(name="UPAT")
    private String upAt;
    @Column(name="UPBY")
    private String upBy;
    @Id
    @Column(name ="UKEY")
    private String uniqueKey;
}
