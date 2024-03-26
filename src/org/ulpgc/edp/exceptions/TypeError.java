package org.ulpgc.edp.exceptions;

/**
 * Exception class thrown when an error related to given object type occurs.
 *
 * @author Javier Castilla
 * @version 22-03-2024
 */
public class TypeError extends RuntimeException {

    /**
     * Constructor by default, no message needed.
     */
    public TypeError() {
        super();
    }

    /**
     * Constructor of the class given a message and a value.
     *
     * @param msg to show when the exception is thrown
     * @param value that caused the exception
     */
    public TypeError(String msg, Object value) {
        super(
                (value.getClass() == String.class) ?
                        String.format("%s: '%s'", msg, value.getClass())
                        : String.format("%s: %s", msg, value.getClass())
        );
    }

    /**
     * Constructor given the value that caused the exception. No message needed.
     *
     * @param value that caused the exception
     */
    public TypeError(Object value) {
        super(
                (value.getClass() == String.class) ?
                        String.format("'%s'", value.getClass())
                        : value.toString()
        );
    }
}
