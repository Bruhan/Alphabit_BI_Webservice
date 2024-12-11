package com.facility.management.persistence.models;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="USER_INFO")
@Getter @Setter @Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="UI_PKEY")
    private int uiPKey;
//plant as dept
    @Column(name="DEPT")
    private String dept;
    @Column(name="USER_ID")
    private String userId;
    @Column(name="PASSWORD")
    private String password;
    @Column(name="USER_NAME")
    private String userName;
    @Column(name="ACCESS_COUNTER")
    private Character accessCounter;
    @Column(name="RANK")
    private String rank;
    @Column(name="DESGINATION")
    private String designation;
    @Column(name="TELNO")
    private String telNo;
    @Column(name="HPNO")
    private String hpNo;
    @Column(name="FAX")
    private String fax;


    @Column(name="EMAIL")
    private String email;
    @Column(name="REMARKS")
    private String remarks;
    @Column(name="USER_LEVEL")
    private String userLevel;

    @Column(name="USER_STATUS")
    private Character userStatus;
    @Column(name="EFFECTIVE_DATE")
    private String effectiveDate;
    @Column(name="ENROLLED_BY")
    private String enrolledBy;

    @Column(name="ENROLLED_ON")
    private String enrolledOn;
    @Column(name="UPDATED_BY")
    private String updatedBy;
    @Column(name="UPDATED_ON")
    private String updatedOn;

    @Column(name="AUTHORISE_BY")
    private String authoriseBy;
    @Column(name="AUTHORISE_ON")
    private String authoriseOn;

    @Column(name="ISAPIUSER")
    private String isApiUser;
    @Column(name="IMAGEPATH")
    private String imagePath;
    @Column(name="LAST_NOTFICATION_CHK")
    private String lastNotificationCheck;

    @Column(name="USER_LEVEL_ACCOUNTING")
    private String userLevelAccounting;
    @Column(name="USER_LEVEL_PAYROLL")
    private String userLevelPayroll;

    @Column(name="WEB_ACCESS")
    private Byte webAccess;
    @Column(name="MANAGER_APP_ACCESS")
    private Byte managerAppAccess;
    @Column(name="RIDER_APP_ACCESS")
    private Byte riderAppAccess;
    @Column(name="ISACCESS_STOREAPP")
    private Byte isAccessStoreApp;
    @Column(name="ISACCESSOWNERAPP")
    private Byte isAccessOwnerApp;
    @Column(name="ISACCESSSUPERVISORAPP")
    private Byte isAccesssupervisorApp;
    @Column(name="ISACCESSPROJECTMANAGERAPP")
    private Byte isAccessProjectManagerApp;
    @Column(name="APP_NOTIFICATION_KEY")
    private String appNotificationKey;
    @Column(name="ISADMIN")
    private Byte isAdmin;
    @Column(name="ISPURCHASEAPPROVAL")
    private Byte isPurchaseApproval;
    @Column(name="ISSALESAPPROVAL")
    private Byte isSalesApproval;
    @Column(name="ISPURCHASERETAPPROVAL")
    private Byte isPurchaseRetApproval;
    @Column(name="ISSALESRETAPPROVAL")
    private Byte isSalesRetApproval;
    @Column(name = "PRD_DEPT_ID")
    private String prdDeptId;
}
