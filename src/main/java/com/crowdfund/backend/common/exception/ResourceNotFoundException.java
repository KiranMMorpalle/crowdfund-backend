package com.crowdfund.backend.common.exception;

public class ResourceNotFoundException extends ApiException{

    public ResourceNotFoundException(String message){
        super(message);
    }
}
