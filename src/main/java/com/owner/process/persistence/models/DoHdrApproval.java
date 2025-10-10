package com.owner.process.persistence.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="##plant##DOHDRAPPROVAL")
@Getter
@Setter
public class DoHdrApproval {
    @Column(name="PLANT")
    private String plant;
    @Column(name="ID")
    private int id;
    @Id
    @Column(name ="UKEY")
    private String uniqueKey;
    @Column(name="DONO")
    private String DoNo;
    @Column(name="VENDNO")
    private String vendNo;
    @Column(name="ORDDATE")
    private String orderDate;
    @Column(name="ORDERTYPE")
    private String orderType;
    @Column(name="DELDATE")
    private String deleteDate;
    @Column(name="STATUS")
    private String status;
    @Column(name="PickStaus")
    private String pickStatus;
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
    @Column(name="CustCode")
    private String customerCode;
    @Column(name="CustName")
    private String customerName;
    @Column(name="JobNum")
    private String jobNo;
    @Column(name="PersonInCharge")
    private String personInCharge;
    @Column(name="contactNum")
    private String contactNo;
    @Column(name="Address")
    private String address;
    @Column(name="Address2")
    private String addressTwo;
    @Column(name="Address3")
    private String addressThree;
    @Column(name="CollectionDate")
    private String collectionDate;
    @Column(name="CollectionTime")
    private String collectionTime;
    @Column(name="Remark1")
    private String remarkOne;
    @Column(name="Remark2")
    private String remarkTwo;
    @Column(name="SHIPPINGID")
    private String shippingId;
    @Column(name="SHIPPINGCUSTOMER")
    private String shippingCustomer;
    @Column(name="CURRENCYID")
    private String currencyId;
    @Column(name="DELIVERYDATE")
    private String deliveryDate;
    @Column(name="TIMESLOTS")
    private String timeSlots;
    @Column(name="OUTBOUND_GST")
    private float outboundGst;
    @Column(name="STATUS_ID")
    private String statusId;
    @Column(name ="EMPNO")
    private String empNo;
    @Column(name ="ESTNO")
    private String estNo;
    @Column(name="PAYMENT_STATUS")
    private String paymentStatus;
    @Column(name="REMARK3")
    private String remarkThree;
    @Column(name="ORDERDISCOUNT")
    private float orderDiscount;
    @Column(name="SHIPPINGCOST")
    private float shippingCost;
    @Column(name="INCOTERMS")
    private String incoterms;
    @Column(name="DNPLREMARKS")
    private String dnplRemarks;
    @Column(name="PAYMENTTYPE")
    private String paymentType;
    /*@Column(name="PRODUCTDELIVERYDATE")
    private int productdeliveryDate;*/
    @Column(name="DELIVERYDATEFORMAT")
    private int deliveryDateFormat;
    @Column(name="APPROVESTATUS")
    private String approveStatus;
    @Column(name="SALES_LOCATION")
    private String salesLocation;
    @Column(name="TAXTREATMENT")
    private String taxTreatment;
    @Column(name="ORDER_STATUS")
    private String orderStatus;
    @Column(name="DISCOUNT")
    private float discount;
    @Column(name="DISCOUNT_TYPE")
    private String discountType;
    @Column(name="ADJUSTMENT")
    private float adjustment;
    @Column(name="ITEM_RATES")
    private int itemRates;
    @Column(name="PROJECTID")
    private int projectId;
    @Column(name="CURRENCYUSEQT")
    private float currencySequence;
    @Column(name="ORDERDISCOUNTTYPE")
    private String orderDiscountType;
    @Column(name="TAXID")
    private int taxId;
    @Column(name="ISDISCOUNTTAX")
    private int isDiscountTax;
    @Column(name="ISORDERDISCOUNTTAX")
    private int isOrderDiscountTax;
    @Column(name="ISSHIPPINGTAX")
    private int isShippingTax;
    @Column(name ="TRANSPORTID")
    private int transportId;
    @Column(name ="PAYMENT_TERMS")
    private String paymentTerms;
    @Column(name ="SHIPCONTACTNAME")
    private String shipContactName;
    @Column(name ="SHIPDESGINATION")
    private String shipDesignation;
    @Column(name ="SHIPWORKPHONE")
    private String shipWorkPhone;
    @Column(name ="SHIPHPNO")
    private String shipHpNo;
    @Column(name ="SHIPEMAIL")
    private String shipEmail;
    @Column(name ="SHIPCOUNTRY")
    private String shipCountry;
    @Column(name ="SHIPADDR1")
    private String shipAddr1;
    @Column(name ="SHIPADDR2")
    private String shipAddr2;
    @Column(name ="SHIPADDR3")
    private String shipAddr3;
    @Column(name ="SHIPADDR4")
    private String shipAddr4;
    @Column(name ="SHIPSTATE")
    private String shipState;
    @Column(name ="SHIPZIP")
    private String shipZip;
    @Column(name ="APP_CUST_ORDER_STATUS")
    private String appCustOrderStatus;
    @Column(name ="DELIVERY_SIGN_URL")
    private String deliverySignUrl;
    @Column(name ="DELIVERY_NAME")
    private String deliveryName;
    @Column(name ="DELIVERY_EMAIL")
    private String deliveryEmail;
    @Column(name ="DELIVERY_LAT")
    private String deliveryLat;
    @Column(name ="DELIVERY_LONG")
    private String deliveryLong;
    @Column(name ="DELIVERY_PHONE")
    private String deliveryPhone;
    /*   @Column(name ="TERMINAL")
        private String terminal;
        @Column(name ="OUTLET")
        private String outlet;
        @Column(name ="ISTILLMONEYCAL")
        private int isTillMoneyCal;
        @Column(name ="SHIFTCLOSEID")
        private int shiftCloseId;
        @Column(name ="VOID_EMPNO")
        private String voidEmpno;
        @Column(name ="VOID_DATE")
        private String voidDate;
        @Column(name ="VOIDCALCULATESTATUS")
        private int voidCalculateStatus;
        @Column(name ="VOIDSHIFTCLOSEID")
        private int voidShiftCloseId;*/
    @Column(name ="REASON")
    private String reason;
}
