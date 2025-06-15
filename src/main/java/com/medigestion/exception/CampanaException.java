package com.medigestion.exception;

public class CampanaException extends RuntimeException {
    public CampanaException(String message) {
        super(message);
    }
    
    public CampanaException(String message, Throwable cause) {
        super(message, cause);
    }
}