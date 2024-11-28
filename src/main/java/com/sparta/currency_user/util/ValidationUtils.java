package com.sparta.currency_user.util;

import com.sparta.currency_user.exception.CustomException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import static com.sparta.currency_user.exception.ErrorCode.INVALID_INPUT_VALUE;

public class ValidationUtils {
    public static void bindErrorMessage(BindingResult bindingResult) {
        StringBuilder errorMessage = new StringBuilder();
        for (FieldError error : bindingResult.getFieldErrors()) {
            errorMessage.append("[")
                    .append(error.getField())
                    .append("] ")
                    .append(":")
                    .append(error.getDefaultMessage());
        }
        throw new CustomException(INVALID_INPUT_VALUE, errorMessage.toString());
    }
}
