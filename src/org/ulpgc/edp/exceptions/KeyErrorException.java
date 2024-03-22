package org.ulpgc.edp.exceptions;

/**
 * Exception class thrown when trying to access a dictionary element when the
 * given key is not contained in it.
 *
 * @author Javier Castilla
 */
public class KeyErrorException extends RuntimeException {

    /**
     * Constructor of the class given a message.
     *
     * @param msg to show when the exception is thrown
     */
    public KeyErrorException(String msg) {
        super(msg);
    }
}
