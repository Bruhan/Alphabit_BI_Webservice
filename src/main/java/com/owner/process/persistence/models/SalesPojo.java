package com.owner.process.persistence.models;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SalesPojo {
    private DoHdr doHdr;
    private List<DoDet> doDetList;
}
