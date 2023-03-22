package com.task.restful.controllers.exception;

import com.task.domain.ResultCode;

public class BadRequestException extends CustomException {

    public BadRequestException(ResultCode resultCode, String message) {
        super(resultCode);
        super.addMessage(message);
    }

}
