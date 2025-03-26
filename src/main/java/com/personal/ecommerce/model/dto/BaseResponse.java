package com.personal.ecommerce.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.personal.ecommerce.constants.Constants;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BaseResponse<T> {
    private String status;
    private String message;
    private T data;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object errors;

    public static <T> BaseResponse<T> success(String message, T data) {
        return new BaseResponse<>(Constants.SUCCESS, message, data, null);
    }

    public static <T> BaseResponse<T> error(String message, Object errors) {
        return new BaseResponse<>(Constants.ERROR, message, null, errors);
    }

    public static <T> BaseResponse<T> error(String message) {
        return new BaseResponse<>(Constants.ERROR, message, null, null);
    }
}