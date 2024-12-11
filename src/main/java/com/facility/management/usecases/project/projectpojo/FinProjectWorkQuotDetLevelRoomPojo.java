package com.facility.management.usecases.project.projectpojo;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public interface FinProjectWorkQuotDetLevelRoomPojo {
    int getID();
    String getPLANT();
    String getWKEY();
    int getFLOOR_ID();
    String getFLOOR_NAME();
    int getROOM_ID();
    String getROOM_NAME();
    String getUOM();
    Float getQTY();
    String getCRAT();
    String getCRBY();
    String getUPAT();
    String getUPBY();
}
