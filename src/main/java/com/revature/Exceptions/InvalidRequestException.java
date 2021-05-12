package com.revature.Exceptions;

/**
 * InvalidRequestException
 * <p>
 * Exception that is thrown when user provides invalid data.
 */
public class InvalidRequestException extends Exception {

    public InvalidRequestException(String message) {
        super(message);
    }


}
