package com.blackshirts.movieshelf.exception;

import com.blackshirts.movieshelf.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BaseException extends RuntimeException {
    public final BaseResponseCode baseResponseCode;
}
