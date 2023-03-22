package com.task.restful.controllers.exception;

import com.task.domain.ResultCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CustomException extends Exception {
    private final HttpStatus httpStatus;
    private final String code;
    private String message;
    private Object extra;

    public CustomException(ResultCode resultCode) {
        this.httpStatus = resultCode.getHttpStatus();
        this.code = resultCode.getCode();
        this.message = resultCode.getDescription();
    }

    public void addMessage(String message) {
        this.message = String.format("%s - %s", this.message, message);
    }

    public <T> void addExtra(T extra) {
        this.extra = extra;
    }
}
