package com.task.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ResultCode {
    OK(HttpStatus.OK, "200000", "성공"),
    INVALID_PARAM(HttpStatus.BAD_REQUEST, "400002", "유효하지 않은 Parameter"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "500000", "internal exception");

    private final HttpStatus httpStatus;
    private final String code;
    private final String description;
}
