package com.facility.management.persistence.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name="##plant##PRODUCTREQUESTRECEIVEHDR")
@Builder
public class ProductRequestReceiveHDR {
    @Column(name = "PLANT")
    private String plant;

    @Column(name = "ID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "PROJECTNO")
    private String projectNo;

    @Column(name = "RECEIVED_DATE")
    private String receivedDate;

    @Column(name = "RECEIVER_ID")
    private String receiverId;

    @Column(name = "RECEIVER_REMARKS")
    private String receiverRemarks;

    @Column(name = "DRIVER_NAME")
    private String driverName;

    @Column(name = "VEHICLE_NUMBER")
    private String vehicleNumber;

    @Column(name="CRAT")
    private String crAt;

    @Column(name="CRBY")
    private String crBy;

    @Column(name="UPAT")
    private String upAt;

    @Column(name="UPBY")
    private String upBy;
}
