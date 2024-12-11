package com.facility.management.persistence.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "##plant##PREDEFINED_SAFETY_CHECKLISTMST")
@Getter
@Setter
public class PredefinedSafetyMst {
	@Column(name="PLANT")
	private String plant;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID")
	private int id;
	@Column(name="CID")
	private int CID;
	@Column(name = "PREDEFINED_SAFETY_CHECKLIST")
	private String predefinedSafetyChecklist;
	@Column(name="CRAT")
	private String crAt;
	@Column(name="CRBY")
	private String crBy;
	@Column(name="UPAT")
	private String upAt;
	@Column(name="UPBY")
	private String upBy;
}