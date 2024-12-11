package com.facility.management.helpers.common.results;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ResultDao {
    private Object results;
    private int statusCode;
    private String message;
}
