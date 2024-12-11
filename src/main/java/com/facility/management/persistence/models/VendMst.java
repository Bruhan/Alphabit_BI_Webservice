package com.facility.management.persistence.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity @Table(name="##plant##VENDMST ") 
@Getter @Setter
public class VendMst {
	@Column(name="PLANT")
	private String plant;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID")
	private int id;
	@Column(name="VENDNO")
	private String vendNo;
	@Column(name="VENDID")
	private String vendId;
	@Column(name="COMMENT1")
	private String commentOne;
	@Column(name="VNAME")
	private String vName;
	@Column(name="NAME")
	private String name;
	@Column(name="DESGINATION")
	private String designation;
	@Column(name="TELNO")
	private String telephoneNo;
	@Column(name="HPNO")
	private String hpNo;
	@Column(name="FAX")
	private String fax;
	@Column(name="EMAIL")
	private String eMail;
	@Column(name="ADDR1")
	private String addressOne;
	@Column(name="ADDR2")
	private String addressTwo;
	@Column(name="ADDR3")
	private String addressThree;
	@Column(name="ADDR4")
	private String addressFour;
	@Column(name="STATE")
	private String state;
	@Column(name="COUNTRY")
	private String country;
	@Column(name="ZIP")
	private String zip;
	@Column(name="REMARKS")
	private String reMark;
	@Column(name="CRAT")
	private String crAt;
	@Column(name="CRBY")
	private String crBy;
	@Column(name="UPAT")
	private String upAt;
	@Column(name="UPBY")
	private String upBy;
	@Column(name="IsActive")
	private String isActive;
	@Column(name="PAY_TERMS")
	private String payTerms;
	@Column(name="PAY_IN_DAYS")
	private String payInDays;
}