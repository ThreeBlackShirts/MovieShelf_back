package com.blackshirts.movieshelf.exception;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BaseException extends RuntimeException {
    public final BaseResponseCode baseResponseCode;
}
