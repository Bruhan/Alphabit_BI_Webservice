package com.facility.management.persistence.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name="HREMPUSERINFO")
@Builder
public class HREmpUserInfo {

    @Column(name = "PLANT")
    private String plant;

    @Column(name = "ID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "EMPNOID")
    private int empNoId;

    @Column(name = "EMPUSERID")
    private String empUserId;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "NOTE")
    private String note;

    @Column(name = "ISSALESMAN")
    private Byte isSalesMan;

    @Column(name = "ISCASHIER")
    private Byte isCashier;

    @Column(name = "ISLOGGEDIN")
    private Byte isLoggedIn;

    @Column(name="CRAT")
    private String crAt;
    @Column(name="CRBY")
    private String crBy;
    @Column(name="UPAT")
    private String upAt;
    @Column(name="UPBY")
    private String upBy;
}
