package com.facility.management.persistence.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "##plant##ATTENDANCE_SUMMARY")
@Getter
@Setter
public class AttendanceSummary {
    @Column(name="PLANT")
    private String plant;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="ID")
    private Integer id;

    @Column(name = "EMPNO")
    private String empNo;

    @Column(name = "IN_TIME")
    private String inTime;

    @Column(name = "OUT_TIME")
    private String outTime;

    @Column(name = "IN_TIME_LOCATION")
    private String inTimeLocation;

    @Column(name = "OUT_TIME_LOCATION")
    private String outTimeLocation;

    @Column(name = "IN_TIME_FACE_PATH")
    private String inTimeFacePath;

    @Column(name = "OUT_TIME_FACE_PATH")
    private String outTimeFacePath;

    @Column(name="CRAT")
    private String crAt;

    @Column(name="CRBY")
    private String crBy;

    @Column(name="UPAT")
    private String upAt;

    @Column(name="UPBY")
    private String upBy;
}
