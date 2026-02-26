package com.crowdfund.backend.common.exception;

public class BusinessValidationException extends ApiException{

    public BusinessValidationException(String message){
        super(message);
    }
}
