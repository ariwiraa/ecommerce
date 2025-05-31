package com.personal.ecommerce.common.errors;

public class ConflictException extends RuntimeException{

    public ConflictException(String message) {
        super(message);
    }
}
