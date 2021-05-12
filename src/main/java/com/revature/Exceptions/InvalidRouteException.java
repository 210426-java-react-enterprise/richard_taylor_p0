package com.revature.Exceptions;

public class InvalidRouteException extends RuntimeException {

    public  InvalidRouteException() {
        super("The route provided was not valid");
    }
}
