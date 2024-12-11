package com.facility.management.persistence.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "##plant##TOOLBOX_CHECKLIST_DET")
@Getter
@Setter
public class ToolboxChecklistDet {
    @Column(name="PLANT")
    private String plant;
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="ID")
    private int id;
    @Column(name = "DETDATE")
    private String deldate;
    @Column(name="HDRID")
    private int hdrid;
    @Column(name="PREDEFINED_SAFETY_CHECKLIST")
    private String predefinedsaftychecklist;
    @Column(name="CHECK_LIST_DATA")
    private String checkListData;
    @Column(name="REMARKS")
    private String remarks;
    @Column(name="STATUS")
    private Short status;
    @Column(name="CRAT")
    private String crAt;
    @Column(name="CRBY")
    private String crBy;
    @Column(name="UPAT")
    private String upAt;
    @Column(name="UPBY")
    private String upBy;
}
