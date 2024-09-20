package com.ajaysw.exception;

/**
 * @author Ajay Wankhade
 */
public class InvalidBookingRequestException extends RuntimeException {
    public InvalidBookingRequestException(String message) {
        super(message);
    }
}
