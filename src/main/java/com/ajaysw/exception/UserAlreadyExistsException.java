package com.ajaysw.exception;

/**
 * @author Ajay Wankhade
 */
public class UserAlreadyExistsException extends RuntimeException{
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
