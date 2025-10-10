package com.owner.process.persistence.models;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SalesOrderList {
    private DoHdr doHdr;
    private List<DoDet> doDet;
    private List<DoDetRemarks> doDetRemarks;
    private CustomerMst customerMst;
    private List<ItemMst> itemMst;
}
