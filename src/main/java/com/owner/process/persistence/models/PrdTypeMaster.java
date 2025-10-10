package com.owner.process.persistence.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="##plant##PRD_TYPE_MST")
@Getter
@Setter
public class PrdTypeMaster {
    @Id
    @Column(name = "PRD_TYPE_ID")
    private String prdTypeId;
    @Column(name = "PRD_TYPE_DESC")
    private String prdTypeDesc;
}
