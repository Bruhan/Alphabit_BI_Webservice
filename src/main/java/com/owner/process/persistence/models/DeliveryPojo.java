package com.owner.process.persistence.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeliveryPojo {
    private int id;
    private String doNo;
    private int doLineNo;
    private String item;
}
