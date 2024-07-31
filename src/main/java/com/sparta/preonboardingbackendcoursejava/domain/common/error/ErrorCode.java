package com.sparta.preonboardingbackendcoursejava.domain.common.error;

import org.springframework.http.HttpStatus;

public enum ErrorCode {

    MODEL_NOT_FOUND(HttpStatus.NOT_FOUND, 1001, "존재하지 않는 정보입니다."),
    AUTHOR_MISMATCH(HttpStatus.FORBIDDEN, 1002, "작성자가 아닙니다."),
    MEMBER_EMAIL_DUPLICATE(HttpStatus.CONFLICT, 2001, "사용할 수 없는 이메일입니다."),
    MEMBER_NICKNAME_DUPLICATE(HttpStatus.CONFLICT, 2002, "사용할 수 없는 닉네임입니다."),
    MEMBER_PASSWORD_MISMATCH(HttpStatus.UNAUTHORIZED, 2003, "비밀번호가 일치하지 않습니다.");

    private final HttpStatus httpStatus;
    private final int code;
    private final String message;

    ErrorCode(HttpStatus httpStatus, int code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}

