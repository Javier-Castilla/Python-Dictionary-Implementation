package org.ulpgc.edp.exceptions;

/**
 * Exception class thrown when an error related to given value occurs.
 *
 * @author Javier Castilla
 * @version 22-03-2024
 */
public class ValueError extends RuntimeException {

    /**
     * Constructor by default, no message needed.
     */
    public ValueError() {
        this("");
    }

    /**
     * Constructor of the class given a message.
     *
     * @param msg to show when the exception is thrown
     */
    public ValueError(String msg) {
        super(msg);
    }
}
