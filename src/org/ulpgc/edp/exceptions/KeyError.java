package org.ulpgc.edp.exceptions;

/**
 * Exception class thrown when trying to access a dictionary element when the
 * given key is not contained in it.
 *
 * @author Javier Castilla
 * @version 28-03-2024
 */
public class KeyError extends RuntimeException {

    /**
     * Constructor by default, no message needed.
     */
    public KeyError() {
        super();
    }

    /**
     * Constructor with message needed.
     */
    public KeyError(String msg) {
        super(msg);
    }

    /**
     * Constructor of the class given a message.
     *
     * @param msg to show when the exception is thrown
     */
    public KeyError(String msg, Object value) {
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
    public KeyError(Object value) {
        super(
                (value == null || value.getClass() == String.class) ?
                        String.format("'%s'", value)
                        : value.toString()
        );
    }
}
