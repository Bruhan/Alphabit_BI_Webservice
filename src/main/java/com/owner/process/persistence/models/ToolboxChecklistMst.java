package com.owner.process.persistence.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "##plant##TOOLBOX_CHECKLIST")
@Getter
@Setter
public class ToolboxChecklistMst {
	@Column(name="PLANT")
	private String plant;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID")
	private int id;
	@Column(name = "HDRDATE")
	private String hdrdate;
	@Column(name="TOOLBOXMEETING_ID")
	private int toolboxMeetingId;
	@Column(name="TOOLBOXMEETING_NAME")
	private String ToolboxMeetingName;
	@Column(name="EMPLOYEECODE")
	private String employeeCode;
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