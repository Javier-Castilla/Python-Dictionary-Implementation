package org.ulpgc.edp.exceptions;

/**
 * Exception class thrown when an error related to given value occurs.
 *
 * @author Javier Castilla
 */
public class ValueErrorException extends RuntimeException {

    /**
     * Constructor of the class given a message.
     *
     * @param msg to show when the exception is thrown
     */
    public ValueErrorException(String msg) {
        super(msg);
    }
}
