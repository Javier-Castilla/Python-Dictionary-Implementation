package org.ulpgc.edp.exceptions;

/**
 * Exception class thrown when an error related to given value occurs.
 *
 * @author Javier Castilla
 * @version 22-03-2024
 */
public class ValueErrorException extends RuntimeException {

    /**
     * Constructor by default, no message needed.
     */
    public ValueErrorException() {
        this("");
    }

    /**
     * Constructor of the class given a message.
     *
     * @param msg to show when the exception is thrown
     */
    public ValueErrorException(String msg) {
        super(msg);
    }
}
