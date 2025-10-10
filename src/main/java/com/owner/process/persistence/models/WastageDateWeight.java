package com.owner.process.persistence.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;

@Setter
@Getter
public class WastageDateWeight {
    @Column(name = "DATE")
    private String date;

    @Column(name = "TOTALQTY")
    private double totalqty;
}
