package com.ajaysw.exception;

/**
 * @author Ajay Wankhade
 */

public class InternalServerException extends RuntimeException {
    public InternalServerException(String message) {
        super(message);
    }
}
