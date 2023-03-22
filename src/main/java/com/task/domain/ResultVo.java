package com.task.domain;

import com.task.restful.controllers.exception.CustomException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter @Getter
public class ResultVo {
    private String code;
    private String message;
    private Object result;

    public <T> ResultVo(ResultCode resultCode, T result) {
        this.code = resultCode.getCode();
        this.message = resultCode.getDescription();
        this.result = result;
    }

    public ResultVo(CustomException ex) {
        this.code = ex.getCode();
        this.message = ex.getMessage();
        this.result = ex.getExtra();
    }
}
