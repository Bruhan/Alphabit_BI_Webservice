package com.owner.process.persistence.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
@Setter
@Getter
public class ProductWastageWeight {
    @Column(name = "ITEM")
    private String item;

    @Column(name = "ITEMDESC")
    private String itemDesc;

    @Column(name = "TOTALQTY")
    private double totalqty;
}
