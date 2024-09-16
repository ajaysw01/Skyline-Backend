package com.ajay.exception;
/**
 *
 * @author Ajay wankhade
 */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
