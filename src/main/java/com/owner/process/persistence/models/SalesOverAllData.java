package com.owner.process.persistence.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;

@Setter
@Getter
public class SalesOverAllData {
    @Column(name = "PROCESSTYPE")
    private String processType;

    @Column(name = "COUNT")
    private Integer count;
}
