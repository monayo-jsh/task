package com.task.restful.controllers.exception.handler;

import com.task.domain.ResultVo;
import com.task.restful.controllers.exception.BadRequestException;
import com.task.restful.controllers.exception.InsideException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody ResultVo handlerBadRequestException(HttpServletRequest request, BadRequestException ex) {
        return new ResultVo(ex);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public @ResponseBody ResultVo handlerInternalServerException(HttpServletRequest request, Exception ex) {
        ex.printStackTrace();

        return new ResultVo(new InsideException());
    }

}
