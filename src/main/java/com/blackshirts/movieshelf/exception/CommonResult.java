package com.blackshirts.movieshelf.exception;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
public class CommonResult<T> {

    @ApiModelProperty(value = "응답 성공 여부: T/F")
    private HttpStatus status;

    @ApiModelProperty(value = "응답 메시지")
    private String msg;

    @ApiModelProperty(value = "응답 result")
    private T data;

}
