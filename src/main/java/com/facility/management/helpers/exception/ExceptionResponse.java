package com.facility.management.helpers.exception;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ExceptionResponse {
    private Integer status;
    private String message;
    private String dateTime;
}
