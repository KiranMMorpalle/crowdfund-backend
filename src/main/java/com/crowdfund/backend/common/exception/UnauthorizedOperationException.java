package com.crowdfund.backend.common.exception;

public class UnauthorizedOperationException extends ApiException{

    public UnauthorizedOperationException(String message){
        super(message);
    }
}
