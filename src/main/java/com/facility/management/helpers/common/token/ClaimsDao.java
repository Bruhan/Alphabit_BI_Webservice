package com.facility.management.helpers.common.token;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ClaimsDao {
    private String iat;
    private String sub;
    private String exp;
    private String plt;
    private String eid;
    private String unt;
    private String loc;
    private String usr;
    private String prj;
    private String iaslin;
    private String iaslot;
}
