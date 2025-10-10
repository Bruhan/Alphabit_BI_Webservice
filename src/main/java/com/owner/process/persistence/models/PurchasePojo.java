package com.owner.process.persistence.models;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PurchasePojo {
    private PoHdr poHdr;
    private List<PoDet> poDetList;
}
