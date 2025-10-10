package com.owner.process.persistence.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;

@Setter
@Getter
public class WastageTypeWeight {
    @Column(name = "WASTAGE_TYPE")
    private String wastageType;

    @Column(name = "WASTAGE_UOM")
    private String uom;

    @Column(name = "TOTALQTY")
    private double totalqty;
}
