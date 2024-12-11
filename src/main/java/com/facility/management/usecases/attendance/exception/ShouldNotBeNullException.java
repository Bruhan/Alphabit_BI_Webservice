package com.facility.management.usecases.attendance.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShouldNotBeNullException extends RuntimeException{
    private String message;

    public ShouldNotBeNullException() {
        super();
    }

    public ShouldNotBeNullException(String msg) {
        super(msg);
        this.message = msg;
    }
}
