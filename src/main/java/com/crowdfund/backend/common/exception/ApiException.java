package com.crowdfund.backend.common.exception;

public abstract class ApiException extends RuntimeException {
    ApiException(String message){
        super(message);
    }

}
