package com.owner.process.helpers.common.results;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ResultDao {
    private Object results;
    private String message;
    private int statusCode;
}
