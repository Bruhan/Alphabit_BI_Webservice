package com.owner.process.persistence.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="PLTAPPROVALMATRIX")
@Getter
@Setter
public class PltApprovalMatrix {

    @Id
    @Column(name="PLANT")
    private String plant;
    @Column(name="ID")
    private int id;
    @Column(name="APPROVALTYPE")
    private String approvalType;
    @Column(name="ISCREATE")
    private int isCreate;
    @Column(name="ISUPDATE")
    private int isUpdate;
    @Column(name="ISDELETE")
    private int isDelete;
    @Column(name="CRAT")
    private String crAt;
    @Column(name="CRBY")
    private String crBy;
    @Column(name="UPAT")
    private String upAt;
    @Column(name="UPBY")
    private String upBy;
}
