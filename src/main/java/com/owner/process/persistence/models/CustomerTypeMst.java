package com.owner.process.persistence.models;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;

@Entity @Table(name="##plant##CUSTOMERTYPEMST") 
@Getter @Setter
public class CustomerTypeMst {
	@Column(name="PLANT")
	private String plant;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID")
	private int id;
	@Column(name="CUSTOMER_TYPE_ID")
	private String customerTypeId;
	@Column(name="CUSTOMER_TYPE_DESC")
	private String customerTypeDescription;
	@Column(name="COMMENTS1")
	private String commentOne;
	@Column(name="COMMENTS2")
	private String commentTwo;
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
}