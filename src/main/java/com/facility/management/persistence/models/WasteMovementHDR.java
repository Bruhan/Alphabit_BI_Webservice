package com.facility.management.persistence.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="##plant##WASTE_MOVEMENT_HDR")
@Getter
@Setter
public class WasteMovementHDR {
    @Column(name = "PLANT")
    private String plant;

    @Column(name = "ID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "PROJECTNO")
    private String projectNo;

    @Column(name = "DATE")
    private String date;

    @Column(name = "VEHICLEID")
    private String vehicleId;

    @Column(name = "DRIVERNO")
    private String driverNo;

    @Column(name = "FINAL_DESTINATION")
    private String finalDestination;

    @Column(name = "REMARKS")
    private String remarks;

    @Column(name = "DC_NO")
    private String deliveryChallanNo;

    @Column(name = "VEHICLE_TYPE")
    private String vehicleType;

    @Column(name="CRAT")
    private String crAt;

    @Column(name="CRBY")
    private String crBy;

    @Column(name="UPAT")
    private String upAt;

    @Column(name="UPBY")
    private String upBy;
}
