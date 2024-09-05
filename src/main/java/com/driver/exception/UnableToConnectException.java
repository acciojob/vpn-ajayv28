package com.driver.exception;

public class UnableToConnectException extends RuntimeException{
    public UnableToConnectException(String message){
        super(message);
    }
}
