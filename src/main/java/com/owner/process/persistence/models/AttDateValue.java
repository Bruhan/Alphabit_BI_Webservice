package com.owner.process.persistence.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import java.util.Date;

@Getter
@Setter
public class AttDateValue {
    @Column(name = "ATT_DATE")
    private Date attDate;

    @Column(name = "VALUE")
    private Integer value;
}
