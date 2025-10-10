package com.owner.process.persistence.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;

@Getter
@Setter
public class PosSalesReportOutTerm {
    @Column(name = "OUTLET")
    private String OUTLET;

    @Column(name = "TERMINAL")
    private String TERMINAL;

    @Column(name = "PAYMENTMODE")
    private String PAYMENTMODE;

    @Column(name = "BILLCOUNT")
    private Integer BILLCOUNT;

    @Column(name = "AMOUNT")
    private Double AMOUNT;

    @Column(name = "PERCENTAGE")
    private Double PERCENTAGE;
}
