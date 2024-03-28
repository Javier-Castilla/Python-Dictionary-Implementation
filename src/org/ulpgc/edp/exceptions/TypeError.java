package org.ulpgc.edp.exceptions;

/**
 * Exception class thrown when an error related to given object type occurs.
 *
 * @author Javier Castilla
 * @version 28-03-2024
 */
public class TypeError extends RuntimeException {

    /**
     * Constructor by default, no message needed.
     */
    public TypeError() {
        super();
    }

    /**
     * Constructor with message needed.
     */
    public TypeError(String msg) {
        super(msg);
    }

    /**
     * Constructor of the class given a message and a value.
     *
     * @param msg to show when the exception is thrown
     * @param type that caused the exception
     */
    public TypeError(String msg, Object type) {
        super(String.format("%s: '%s'", msg, (type == null) ? null : type.getClass()));
    }

    /**
     * Constructor given the value that caused the exception. No message needed.
     *
     * @param type that caused the exception
     */
    public TypeError(Object type) {
        super(String.format("'%s'", (type == null) ? null : type.getClass()));
    }
}
