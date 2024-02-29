package org.ulpgc.edp.exceptions;


/**
 * Exception class thrown when an error related with a key of a dictionary occurs.
 *
 * @author Javier Castilla
 * @author David Miranda
 * @author Esteban Trujillo
 * @author Elena Artiles
 */
public class KeyErrorException extends Exception {

    /**
     * Constructor of the exception class.
     *
     * @param msg to show when the exception is thrown
     */
    public KeyErrorException(String msg) {
        super(msg);
    }
}