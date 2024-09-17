package com.ajay.exception;

/**
 *
 * @author Ajay wankhade
 */
public class InternalServerException extends RuntimeException {
    public InternalServerException(String message) {
        super(message);
    }
}
