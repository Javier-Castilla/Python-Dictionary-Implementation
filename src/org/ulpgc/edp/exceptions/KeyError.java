package org.ulpgc.edp.exceptions;

/**
 * Exception class thrown when trying to access a dictionary element when the
 * given key is not contained in it.
 *
 * @author Javier Castilla
 * @version 22-03-2024
 */
public class KeyError extends RuntimeException {

    /**
     * Constructor by default, no message needed.
     */
    public KeyError() {
        super();
    }

    /**
     * Constructor of the class given a message.
     *
     * @param msg to show when the exception is thrown
     */
    public KeyError(String msg, Object value) {
        super(
                (value.getClass() == String.class) ?
                        String.format("%s: '%s'", msg, value)
                        : String.format("%s: %s", msg, value)
        );
    }

    public KeyError(Object value) {
        super(
                (value.getClass() == String.class) ?
                        String.format("'%s'", value)
                        : value.toString()
        );
    }
}
