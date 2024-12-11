package com.facility.management.persistence.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="##plant##PROJECTSTOCKREQUESTHDR")
@Getter
@Setter
@Builder
public class ProjectStockRequestHDR {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="ID")
    private int ID;
    @Column(name="PLANT")
    private String PLANT;
    @Column(name="PROJECTID")
    private int PROJECTID;
    @Column(name="PROJECTNO")
    private String PROJECTNO;
    @Column(name="REQUESTSTATUS")
    private String REQUESTSTATUS;
    @Column(name="REQUESTEDDATE")
    private String REQUESTEDDATE;
    @Column(name="REQUESTOR_ID")
    private String REQUESTOR_ID;
    @Column(name="APPROVER_CODE")
    private String APPROVER_CODE;
    @Column(name="APPROVAL_STATUS")
    private String APPROVAL_STATUS;
    @Column(name="APPROVAL_DATE")
    private String APPROVAL_DATE;
    @Column(name="APPROVER_REMARKS")
    private String APPROVER_REMARKS;
    @Column(name="CRAT")
    private String CRAT;
    @Column(name="CRBY")
    private String CRBY;
    @Column(name="UPAT")
    private String UPAT;
    @Column(name="UPBY")
    private String UPBY;
    @Column(name="STATUS")
    private String status;
    @Column(name="REQUESTER_REMARKS")
    private String requesterRemarks;
}
