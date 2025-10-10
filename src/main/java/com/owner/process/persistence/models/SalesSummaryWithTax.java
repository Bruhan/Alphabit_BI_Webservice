package com.owner.process.persistence.models;

public interface SalesSummaryWithTax {
    String getCUSTOMERNAME();
    String getITEM();
    String getITEMDESC();
    float getQTY();
    float getPRICE();
    float getTAX_AMOUNT();
    float getTOTAL_PRICE();
}
