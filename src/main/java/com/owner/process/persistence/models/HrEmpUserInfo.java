package com.owner.process.persistence.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="HREMPUSERINFO")
@Getter
@Setter
public class HrEmpUserInfo {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="ID")
    private int id;
    @Column(name="PLANT")
    private String plant;
    @Column(name="EMPNOID")
    private int empNoId;
    @Column(name="EMPUSERID")
    private String userId;
    @Column(name="PASSWORD")
    private String password;
    @Column(name="NOTE")
    private String note;
    @Column(name="ISSALESMAN")
    private Short isSalesman;
    @Column(name="ISCASHIER")
    private Short isCashier;
    @Column(name="ISLOGGEDIN")
    private Short isLoggedIn;
    @Column(name="CRAT")
    private String crAt;
    @Column(name="CRBY")
    private String crBy;
    @Column(name="UPAT")
    private String upAt;
    @Column(name="UPBY")
    private String upBy;
}
