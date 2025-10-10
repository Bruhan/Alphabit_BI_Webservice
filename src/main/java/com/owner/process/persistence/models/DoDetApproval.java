package com.owner.process.persistence.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Entity @Table(name="##plant##DODETAPPROVAL")
@Getter
@Setter
@IdClass(DoDetApprovalIds.class)
public class DoDetApproval {
    @Column(name="PLANT")
    private String plant;
    @Column(name="ID")
    private String id;
    @Id
    @Column(name ="UKEY")
    private String uniqueKey;
    @Column(name="DONO")
    private String doNo;
    @Column(name="DOLNNO")
    private int doLineNo;
    @Column(name="PickStatus")
    private String pickStatus;
    @Column(name="LNSTAT")
    private String lineStatus;
    @Column(name="ITEM")
    private String item;
    @Column(name="ItemDesc")
    private String itemDescription;
    @Column(name="TRANDATE")
    private String transactionDate;
    @Column(name="TRANTYPE")
    private String transactionType;
    @Column(name="UNITPRICE")
    private float unitPrice;
    @Column(name="ASNMT")
    private String asnmt;
    @Column(name="QTYOR")
    private float quantityOr;
    @Column(name="QTYIS")
    private float quantityIs;
    @Column(name="QtyPick")
    private float quantityPick;
    /*@Column(name="QTYAC")
    private float quantityAc;
    @Column(name="QTYRJ")
    private float quantityRj;*/
    @Column(name="LOC")
    private String location;
    @Column(name="WHID")
    private String whId;
    @Column(name="POLTYPE")
    private String polType;
    @Column(name="UNITMO")
    private String unitMo;
    @Column(name="DELDATE")
    private String delDate;
    @Column(name="COMMENT1")
    private String commentOne;
    @Column(name="COMMENT2")
    private String commentTwo;
    @Column(name="CRAT")
    private String crAt;
    @Column(name="CRBY")
    private String crBy;
    @Column(name="UPAT")
    private String upAt;
    @Column(name="UPBY")
    private String upBy;
    @Column(name="RECSTAT")
    private String recStat;
    @Column(name="USERFLD1")
    private String userFieldOne;
    @Column(name="USERFLD2")
    private String userFieldTwo;
    @Column(name="USERFLD3")
    private String userFieldThree;
    @Column(name="USERFLD4")
    private String userFieldFour;
    @Column(name="USERFLD5")
    private String userFieldFive;
    @Column(name="USERFLD6")
    private String userFieldSix;
    @Column(name="USERFLG1")
    private String userFlagOne;
    @Column(name="USERFLG2")
    private String userFlagTwo;
    @Column(name="USERFLG3")
    private String userFlagThree;
    @Column(name="USERFLG4")
    private String userFlagFour;
    @Column(name="USERFLG5")
    private String userFlagFive;
    @Column(name="USERFLG6")
    private String userFlagSix;
    @Column(name="USERTIME1")
    private String userTimeOne;
    @Column(name="USERTIME2")
    private String userTimeTwo;
    @Column(name="USERTIME3")
    private String userTimeThree;
    /*@Column(name="USERDBL1")
    private float userdblOne;
    @Column(name="USERDBL2")
    private float userdblTwo;
    @Column(name="USERDBL3")
    private float userdblThree;
    @Column(name="USERDBL4")
    private float userdblFour;
    @Column(name="USERDBL5")
    private float userdblFive;
    @Column(name="USERDBL6")
    private float userdblSix;*/
    @Column(name="CURRENCYUSEQT")
    private float currencySequence;
    @Column(name ="ESTNO")
    private String estNo;
    @Column(name ="ESTLNNO")
    private int EstLnno;
    @Column(name="PRODGST")
    private float productGst;
    @Column(name="PACKING")
    private String packing;
    @Column(name="DIMENSION")
    private String dimension;
    /*@Column(name="NETWEIGHT")
    private float netWeight;
    @Column(name="GROSSWEIGHT")
    private float grossWeight;*/
    @Column(name="PRODUCTDELIVERYDATE")
    private String productDeliveryDate;
    @Column(name="ACCOUNT_NAME")
    private String accountName;
    @Column(name="TAX_TYPE")
    private String taxType;
    @Column(name="DISCOUNT")
    private float discount;
    @Column(name="DISCOUNT_TYPE")
    private String discountType;
    @Column(name="ADDONAMOUNT")
    private float addOnAmount;
    @Column(name="ADDONTYPE")
    private String addOnType;
    @Column(name="UNITCOST")
    private float unitCost;
    @Column(name ="SALESMAN")
    private String salesMan;
    /*@Column(name ="ISFOC")
    private int isFoc;
    @Column(name ="FOCLNNO")
    private int focLnno;*/
}
