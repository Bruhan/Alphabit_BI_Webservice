package com.owner.process.persistence.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity @Table(name="##plant##ITEMMST") 
@Getter @Setter
public class ItemMst {
	@Column(name="PLANT")
	private String plant;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID")
	private int id;
	@Column(name="ITEM")
	private String item;
	@Column(name="ITEMDESC")
	private String itemDescription;
	@Column(name="UnitPrice")
	private Float unitPrice;
	@Column(name="COST")
	private Float cost;
	@Column(name="DISCOUNT")
	private Float discount;
	@Column(name="REMARK1")
	private String remarkOne;
	@Column(name="STKQTY")
	private Float stackQuantity;
	@Column(name="PRD_CLS_ID")
	private String productClassId;
	@Column(name="ITEMTYPE")
	private String ItemType;
	/*@Column(name="ASSET")
	private String asset;
	@Column(name="REMARK2")
	private String remarkTwo;*/
	@Column(name="REMARK3")
	private String remarkThree;
	@Column(name="REMARK4")
	private String remarkFour;
	/*@Column(name="ITEMCONDITION")
	private String itemCondition;
	@Column(name="STATUS")
	private String status;
	@Column(name="RANK")
	private String rank;
	@Column(name="LENGTH")
	private String length;
	@Column(name="WIDTH")
	private String width;
	@Column(name="HEIGHT")
	private String height;
	@Column(name="VOLUME")
	private String volume;
	@Column(name="AREA")
	private String area;*/
	@Column(name="ARTIST")
	private String artist;
	@Column(name="TITLE")
	private String title;
	@Column(name="MEDIUM")
	private String medium;
	/*@Column(name="ISIZE")
	private String iSize;
	@Column(name="RORDLVL")
	private String rOrderLevel;
	@Column(name="SLIFEIND")
	private String sLifeInd;*/
	/*@Column(name="TRKIND")
	private String trkInd;
	@Column(name="QCIND")
	private String qcInd;
	@Column(name="QUARIND")
	private String quarInd;
	@Column(name="HAZITEM")
	private String hazItem;*/
	/*@Column(name="SLIFE")
	private Integer sLife;*/
	/*@Column(name="QUARDAYS")
	private Integer quarDays;*/
	@Column(name="STKUOM")
	private String stackUom;
	/*@Column(name="UNITWGT")
	private String unitWeight;
	@Column(name="COMMENT1")
	private String commentOne;
	@Column(name="COMMENT2")
	private String commentTwo;*/
	@Column(name="CRAT")
	private String crAt;
	@Column(name="CRBY")
	private String crBy;
	@Column(name="UPAT")
	private String upAt;
	@Column(name="UPBY")
	private String upBy;
	/*@Column(name="RECSTAT")
	private String recStatus;*/
	@Column(name="USERFLD1")
	private String userFieldOne;
	@Column(name="IsActive")
	private String isActive;
	@Column(name="NONSTKFLAG")
	private String nonStackFlag;
	@Column(name="NONSTKTYPEID")
	private String nonStackTypeId;
	@Column(name="MINSPRICE")
	private Float minsPrice;
	@Column(name="PRD_BRAND_ID")
	private String productBrandId;
	@Column(name="ITEM_LOC")
	private String itemLocation;
	@Column(name="MAXSTKQTY")
	private Float maximumStackQuantity;
	/*@Column(name="PRINTSTATUS")
	private String printStatus;*/
	@Column(name="PRODGST")
	private Float productGst;
	@Column(name="NETWEIGHT")
	private Float netWeight;
	@Column(name="GROSSWEIGHT")
	private Float grossWeight;
	@Column(name="HSCODE")
	private String hsCode;
	@Column(name="COO")
	private String coo;
	/*@Column(name="CONSIGNEEPRICE")
	private Float consigneePrice;*/
	@Column(name="VINNO")
	private String vinNo;
	@Column(name="MODEL")
	private String model;
	@Column(name="PURCHASEUOM")
	private String purchaseUom;
	@Column(name="SALESUOM")
	private String salesUom;
	@Column(name="RENTALUOM")
	private String rentalUom;
	@Column(name="SERVICEUOM")
	private String serviceUom;
	@Column(name="INVENTORYUOM")
	private String inventoryUom;
	@Column(name="RENTALPRICE")
	private Float rentalPrice;
	@Column(name="SERVICEPRICE")
	private Float servicePrice;
	@Column(name="ISBASICUOM")
	private int isBasicUom;
	@Column(name="CATLOGPATH")
	private String catalogPath;
	@Column(name="INCPRICE")
	private String incPrice;
	@Column(name="INCPRICEUNIT")
	private String incPriceUnit;
	@Column(name="ISCOMPRO")
	private Short isComPro;
	@Column(name="PRD_DEPT_ID")
	private String prdDeptId;
}