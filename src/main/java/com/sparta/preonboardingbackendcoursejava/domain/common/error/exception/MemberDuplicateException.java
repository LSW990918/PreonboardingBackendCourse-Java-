package com.sparta.preonboardingbackendcoursejava.domain.common.error.exception;

import com.sparta.preonboardingbackendcoursejava.domain.common.error.ErrorCode;

public class MemberDuplicateException extends RuntimeException {

    private final ErrorCode errorCode;

    public MemberDuplicateException(ErrorCode errorCode) {this.errorCode = errorCode;}

    public ErrorCode getErrorCode() {return errorCode;}
}

