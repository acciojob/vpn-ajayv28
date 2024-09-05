package com.driver.exception;

public class AlreadyDisconnectedException extends RuntimeException{
    public AlreadyDisconnectedException(String message){
        super(message);
    }
}
