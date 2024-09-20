package com.ajaysw.exception;

/**
 * @author Ajay Wankhade
 */
public class RoleAlreadyExistException extends RuntimeException {
    public RoleAlreadyExistException(String message) {
        super(message);
    }
}
