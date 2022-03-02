package com.blackshirts.movieshelf.controller;

import com.blackshirts.movieshelf.exception.BaseException;
import com.blackshirts.movieshelf.exception.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {BaseException.class})
    protected ResponseEntity<BaseResponse> handleBaseException(BaseException e) {
        return ResponseEntity.status(e.baseResponseCode.getHttpStatus())
                .body(new BaseResponse(e.baseResponseCode.getHttpStatus(), e.baseResponseCode.getMessage(), e.baseResponseCode.getMessage()));
    }
}
