package org.example.exception;

public class NTTAppBaseException extends RuntimeException {
    public NTTAppBaseException(){
    }

    public NTTAppBaseException(String message) {
        super(message);
    }
}
