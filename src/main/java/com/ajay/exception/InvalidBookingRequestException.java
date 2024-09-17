package com.ajay.exception;
/**
 *
 * @author Ajay wankhade
 */
public class InvalidBookingRequestException extends RuntimeException{
    public InvalidBookingRequestException(String message) {
        super(message);
    }
}
