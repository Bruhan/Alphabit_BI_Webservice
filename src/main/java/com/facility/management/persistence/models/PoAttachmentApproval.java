package com.facility.management.persistence.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="##plant##PURCHASEORDERATTACHMENTSAPPROVAL")
@Getter
@Setter
@IdClass(PoAttachmentApprovalIds.class)
public class PoAttachmentApproval {
    @Column(name="PLANT")
    private String plant;
    @Id
    @Column(name="ID")
    private int id;
    @Column(name="PONO")
    private String poNo;
    @Column(name="FileType")
    private String fileType;
    @Column(name="FileName")
    private String fileName;
    @Column(name="FileSize")
    private String fileSize;
    @Column(name="FilePath")
    private String filePath;
    @Column(name="CRAT")
    private String crAt;
    @Column(name="CRBY")
    private String crBy;
    @Column(name="UPAT")
    private String upAt;
    @Column(name="UPBY")
    private String upBy;
    @Id
    @Column(name ="UKEY")
    private String uniqueKey;
}
