package com.sparta.preonboardingbackendcoursejava.domain.common.error.exception;

import com.sparta.preonboardingbackendcoursejava.domain.common.error.ErrorCode;

public class PasswordMismatchException extends RuntimeException {

    private final ErrorCode errorCode;

    public PasswordMismatchException(ErrorCode errorCode) {this.errorCode = errorCode;}

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}

