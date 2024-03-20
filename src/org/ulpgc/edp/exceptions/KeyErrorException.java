package org.ulpgc.edp.exceptions;

/**
 * Exception class thrown when trying to access a dictionary's element when the
 * given key is not contained in it.
 *
 * @author Javier Castilla
 */
public class KeyErrorException extends RuntimeException {
    private Object key;

    /**
     * Constructor of the class given a message and a key.
     *
     * @param msg to show when the exception is thrown
     * @param key that caused the exception
     */
    public KeyErrorException(String msg, Object key) {
        this(msg + ": " + key);
        this.key = key;
    }

    /**
     * Constructor of the class given a message and a key.
     *
     * @param msg to show when the exception is thrown
     */
    public KeyErrorException(String msg) {
        super(msg);
    }

    /**
     * Getter of the key that caused the exception.
     *
     * @return the key that caused the exception
     */
    public Object value() {
        return key;
    }
}
