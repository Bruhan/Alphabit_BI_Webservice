package com.facility.management.persistence.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public interface FinProjectItemPojo {
    int getID();
    String getPLANT();
    int getLNNO();
    int getPROJECTHDRID();
    String getITEM();
    String getITEMDESC();
    String getACCOUNT_NAME();
    Float getQTY();
    String getUOM();
    Float getUNITPRICE();
    Float getDISCOUNT();
    String getDISCOUNT_TYPE();
    String getTAX_TYPE();
    Float getCURRENCYUSEQT();
    Float getAMOUNT();
    String getNOTE();
    String getCRAT();
    String getCRBY();
    String getUPAT();
    String getUPBY();
}
