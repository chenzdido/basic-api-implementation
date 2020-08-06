package com.thoughtworks.rslist.exception;

public class RsEventNotValueException extends RuntimeException {
    private String errorMessage;
    public RsEventNotValueException(String errorMessage){
        this.errorMessage=errorMessage;
    }
    @Override
    public String getMessage(){
        return errorMessage;
    }

}
