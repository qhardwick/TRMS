package com.infy.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorMessage {

    private String message;
    private int errorCode;

    public ErrorMessage(int errorCode, String message) {
        super();
        this.errorCode = errorCode;
        this.message = message;
    }
}
