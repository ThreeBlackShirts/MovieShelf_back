package com.blackshirts.movieshelf.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum BaseResponseCode {

    /**
     * 200 OK : 요청 성공
     */
    OK(HttpStatus.OK, "요청 성공하였습니다."),

    /**
     * 400 BAD_REQUEST : 잘못된 요청
     */
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "잘못된 비밀번호입니다. 다시 입력해주세요."),
    DUPLICATE_EMAIL(HttpStatus.BAD_REQUEST, "중복된 이메일입니다. 다시 입력해주세요."),
    /**
     * 404 NOT FOUND
     */
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."),
    /**
     * 404 NOT FOUND
     */
    FAILED_TO_SAVE_USER(HttpStatus.NOT_FOUND, "사용자를 등록에 실패했습니다."),
    REVIEW_NOT_FOUND(HttpStatus.NOT_FOUND, "영화 후기를 찾을 수 없습니다."),
    Like_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 리뷰에 대한 좋아요를 찾을 수 없습니다."),
    FAILED_TO_SAVE_LIKE(HttpStatus.NOT_FOUND, "좋아요 등록에 실패했습니다."),

    /**
     * 405 Method Not Allowed
     */
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "허용되지 않은 메서드입니다.");

    private HttpStatus httpStatus;
    private String message;
}
