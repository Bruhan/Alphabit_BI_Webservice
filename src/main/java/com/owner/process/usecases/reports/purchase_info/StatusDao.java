package com.owner.process.usecases.reports.purchase_info;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class StatusDao {
    private String name;
    private int id;

    public StatusDao(int id) {
        this.id = id;
    }
}
