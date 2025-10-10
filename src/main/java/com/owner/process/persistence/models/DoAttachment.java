package com.owner.process.persistence.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="##plant##SALESORDERATTACHMENTS")
@Getter
@Setter
public class DoAttachment {

    @Column(name="PLANT")
    private String plant;
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="ID")
    private int id;
    @Column(name="DONO")
    private String doNo;
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
    @Column(name ="UKEY")
    private String uniqueKey;
}
