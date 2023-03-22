package com.task.restful.controllers.exception;

import com.task.domain.ResultCode;

public class InsideException extends CustomException {

    public InsideException() {
        super(ResultCode.INTERNAL_SERVER_ERROR);
    }

    public InsideException(String message) {
        super(ResultCode.INTERNAL_SERVER_ERROR);
        super.addMessage(message);
    }

}
