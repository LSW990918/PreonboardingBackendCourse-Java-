package com.sparta.preonboardingbackendcoursejava.domain.common.error;

public class ErrorObject {

    private final int code;
    private final String message;


    public ErrorObject(int code, String message) {
        this.code = code;
        this.message = message;
    }


    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
