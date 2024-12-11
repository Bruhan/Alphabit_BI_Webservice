package com.facility.management.persistence.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "##plant##Staffattendance")
@Getter
@Setter
@Builder
public class StaffAttendance {
    @Column(name="PLANT")
    private String plant;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="ID")
    private int id;

    @Column(name="EMPID")
    private int empId;

    @Column(name="Att_Date")
    private String attendanceDate;

    @Column(name="Att_Time")
    private String attendanceTime;

    @Column(name = "ShiftStatus")
    private String shiftStatus;

    @Column(name = "Location_Lat")
    private String locationLat;

    @Column(name="Location_Long")
    private String locationLong;

    @Column(name="Permission")
    private String permission;

    @Column(name="Narrations")
    private String narrations;

    @Column(name="StaffMonthid")
    private int staffMonthId;
}
