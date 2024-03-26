package org.ulpgc.edp.exceptions;

/**
 * Exception class thrown when indexes are not correct.
 *
 * @author Javier Castilla
 * @version 22-03-2024
 */
public class IndexError extends IndexOutOfBoundsException {

    /**
     * Constructor by default, no message needed.
     */
    public IndexError() {
        this("");
    }

    /**
     * Constructor of the class given a message.
     *
     * @param msg to show when the exception is thrown
     */
    public IndexError(String msg) {
        super(msg);
    }
}
