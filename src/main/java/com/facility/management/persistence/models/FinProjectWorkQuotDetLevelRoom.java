package com.facility.management.persistence.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigInteger;

@Entity
@Table(name="##plant##WORKQUOTDET_LEVELROOM")
@Getter
@Setter
public class FinProjectWorkQuotDetLevelRoom {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="ID")
    private int ID;
    @Column(name="PLANT")
    private String PLANT;
    @Column(name="WKEY")
    private String WKEY;
    @Column(name="FLOOR_ID")
    private int FLOOR_ID;
    @Column(name="ROOM_ID")
    private int ROOM_ID;
    @Column(name="UOM")
    private String UOM;
    @Column(name="QTY")
    private Float QTY;
    @Column(name="CRAT")
    private String CRAT;
    @Column(name="CRBY")
    private String CRBY;
    @Column(name="UPAT")
    private String UPAT;
    @Column(name="UPBY")
    private String UPBY;
}
