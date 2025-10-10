package com.owner.process.persistence.models;

public interface PurchaseSummaryWithTax {
   String getSUPPLIERNAME();
   String getITEM();
   String getITEMDESC();
   float getQTY();
   float getCOST();
   float getTAX_AMOUNT();
   float getTOTAL_COST();
}
