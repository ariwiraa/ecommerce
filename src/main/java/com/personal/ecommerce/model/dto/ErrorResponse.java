package com.personal.ecommerce.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class ErrorResponse {
    private Integer code;
    private String message;
    private Timestamp timestamp;
}
