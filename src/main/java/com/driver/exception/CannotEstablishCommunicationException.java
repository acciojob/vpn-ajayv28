package com.driver.exception;

public class CannotEstablishCommunicationException extends RuntimeException{
    public CannotEstablishCommunicationException(String message){
        super(message);
    }
}
