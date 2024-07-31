package com.sparta.preonboardingbackendcoursejava.domain.common.error;


import com.sparta.preonboardingbackendcoursejava.domain.common.error.exception.ModelNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionAdvice {

    @ExceptionHandler(ModelNotFoundException.class)
    public ResponseEntity<ErrorObject> handleModelNotFoundException(ModelNotFoundException e) {
        ErrorCode errorCode = e.getErrorCode();
        ErrorObject errorObject = new ErrorObject(errorCode.getCode(), errorCode.getMessage());
        return ResponseEntity.status(errorCode.getHttpStatus()).body(errorObject);
    }
}
