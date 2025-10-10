package com.owner.process.persistence.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="CUSTOMER_INFO")
@Getter
@Setter
public class CustomerInfo {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="ID")
    private int id;
    @Column(name="PLANT")
    private String plant;
    @Column(name="CUSTNO")
    private String custNo;
    @Column(name="CUSTDESC")
    private String custDesc;
    @Column(name="USER_ID")
    private String userId;
    @Column(name="USER_NAME")
    private String userName;
    @Column(name="PASSWORD")
    private String password;
    @Column(name="ISMANAGERAPPACCESS")
    private Short isManagerAppAccess;
    @Column(name="APP_NOTIFICATION_KEY")
    private String appNotificationKey;
    @Column(name="USER_HPNO")
    private String userHPNO;
    @Column(name="USER_EMAIL")
    private String userEmail;
    @Column(name="CRAT")
    private String crAt;
    @Column(name="CRBY")
    private String crBy;
    @Column(name="UPAT")
    private String upAt;
    @Column(name="UPBY")
    private String upBy;
}
