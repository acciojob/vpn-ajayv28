package com.driver.exception;

public class AlreadyConnectedException extends RuntimeException{
    public AlreadyConnectedException(String message){
        super(message);
    }
}
