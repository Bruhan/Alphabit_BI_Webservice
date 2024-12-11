package com.facility.management.persistence.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "##plant##TOOLBOX_MEETING")
@Getter
@Setter
public class ToolBoxMeeting {
    @Column(name="PLANT")
    private String plant;
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="ID")
    private int id;
    @Column(name = "MEETINGDATE")
    private String meetingDate;
    @Column(name="PROJECTCODE")
    private String projectCode;
    @Column(name="TOOLBOXMEETING_NAME")
    private String toolBoxMeetingName;
    @Column(name="SUPERVISORCODE")
    private String supervisorCode;
    @Column(name="STATUS")
    private String status;
    @Column(name="CRAT")
    private String crAt;
    @Column(name="CRBY")
    private String crBy;
    @Column(name="UPAT")
    private String upAt;
    @Column(name="UPBY")
    private String upBy;
}
