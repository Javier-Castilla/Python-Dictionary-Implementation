package org.ulpgc.edp.exceptions;

/**
 * Exception class thrown when trying to access an empty dictionary's elements.
 *
 * @author Javier Castilla
 */
public class EmptyDictionaryException extends Exception {
    /**
     * Constructor of the class.
     *
     * @param msg to show when the exception is thrown
     */
    public EmptyDictionaryException(String msg) {
        super(msg);
    }
}
