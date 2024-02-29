package org.ulpgc.edp.exceptions;

/**
 * Exception class thrown when trying to access an empty dictionary elements.
 *
 * @author Javier Castilla
 * @author David Miranda
 * @author Esteban Trujillo
 * @author Elena Artiles
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
