package org.example.exception;

public class OwnerInvalidCredentialsException extends NTTAppBaseException {
    public OwnerInvalidCredentialsException() {
    }
    public OwnerInvalidCredentialsException(String message) {
        super(message);
    }
}
