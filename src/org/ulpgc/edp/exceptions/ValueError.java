package org.ulpgc.edp.exceptions;

/**
 * Exception class thrown when an error related to given value occurs.
 *
 * @author Javier Castilla
 * @version 28-03-2024
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

    /**
     * Constructor of the class given a message.
     *
     * @param msg to show when the exception is thrown
     */
    public ValueError(String msg, Object value) {
        super(
                (value == null || value.getClass() == String.class) ?
                        String.format("%s: '%s'", msg, value)
                        : String.format("%s: %s", msg, value)
        );
    }

    /**
     * Constructor of the class given the problematic value.
     *
     * @param value that caused the exception
     */
    public ValueError(Object value) {
        super(
                (value == null || value.getClass() == String.class) ?
                        String.format("'%s'", value)
                        : value.toString()
        );
    }
}
