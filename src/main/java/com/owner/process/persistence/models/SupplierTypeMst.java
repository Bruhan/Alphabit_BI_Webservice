package com.owner.process.persistence.models;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;

@Entity @Table(name="##plant##SUPPLIERTYPEMST ") 
@Getter @Setter
public class SupplierTypeMst {
	@Column(name="PLANT")
	private String plant;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID")
	private int id;
	@Column(name="SUPPLIER_TYPE_ID")
	private String supplierTypeId;
	@Column(name="SUPPLIER_TYPE_DESC")
	private String supplierTypeDescription;
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