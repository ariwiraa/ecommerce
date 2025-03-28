package com.personal.ecommerce.config.middleware;

import com.personal.ecommerce.common.errors.BadRequestException;
import com.personal.ecommerce.common.errors.ResourceNotFoundException;
import com.personal.ecommerce.model.dto.BaseResponse;
import com.personal.ecommerce.model.dto.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestControllerAdvice
@Slf4j
public class GenericExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public @ResponseBody BaseResponse<Object> handlerResourceNotFoundException(HttpServletRequest httpServletRequest,
                                                                       ResourceNotFoundException exception) {

        return BaseResponse.error(exception.getMessage());
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody BaseResponse<Object> handlerBadRequestException(HttpServletRequest httpServletRequest,
                                                                        BadRequestException exception) {
        return BaseResponse.error(exception.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public @ResponseBody BaseResponse<Object> handlerInternalServerErrorException(HttpServletRequest httpServletRequest,
                                                                  Exception exception) {
        return BaseResponse.error(exception.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody BaseResponse<Object> handlerInternalServerErrorException(MethodArgumentNotValidException ex) {

        return BaseResponse.error(ex.getMessage());
    }
}
