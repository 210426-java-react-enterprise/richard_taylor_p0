package com.revature.Exceptions;

/**
 * ResourcePersistenceException
 * <p>
 * An exception thrown when there is a problem persisting data to the database.
 */
public class ResourcePersistenceException extends Exception {

    public ResourcePersistenceException(String message) {
        super(message);
    }

    public ResourcePersistenceException() {

    }

}
