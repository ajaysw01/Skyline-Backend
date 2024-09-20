package com.ajaysw.exception;

/**
 * @author Ajay Wankhade
 */

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
