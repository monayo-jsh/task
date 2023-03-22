package com.task.restful.utils;

import java.util.stream.Collectors;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

public class CommonUtil {

    public static String makeBindingResultMessage(BindingResult bindingResult) {
        return bindingResult.getAllErrors().stream()
                     .map(error -> String.format("%s : %s", ((FieldError) error).getField(), error.getDefaultMessage()))
                     .collect(Collectors.joining(", ", "[", "]"));
    }

}
