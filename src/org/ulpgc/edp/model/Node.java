package org.ulpgc.edp.model;

/**
 * Node class used to storage a pair key - value and its index of and indexes
 * hash table.
 *
 * @author Javier Castilla
 * @author David Miranda
 * @author Esteban Trujillo
 * @author Elena Artiles
 */
class Node {
    private Object key;
    private Object value;
    private int index;

    /**
     * Constructor of the Node Class.
     *
     * @param key
     * @param value
     * @param index
     */
    Node(Object key, Object value, int index) {
        this.key = key;
        this.value = value;
        this.index = index;
    }

    /**
     * Getter for key attribute
     *
     * @return key
     */
    Object key() {
        return key;
    }

    /**
     * Setter for key attribute
     *
     * @param key
     */
    void key(Object key) {
        this.key = key;
    }

    /**
     * Getter for value attribute
     *
     * @return value
     */
    Object value() {
        return value;
    }

    /**
     * Setter for value attribute
     *
     * @param value
     */
    void value(Object value) {
        this.value = value;
    }

    /**
     * Getter for index attribute
     *
     * @return index
     */
    int index() {
        return index;
    }

    /**
     * Setter for index value
     *
     * @param index
     */
    void index(int index) {
        this.index = index;
    }

    /**
     * String representation of the Node class.
     * @return a string representation
     */
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();

        if (key.getClass() == String.class) {
            str.append(String.format("\'%s\'", key));
        } else {
            str.append(key);
        }

        str.append(": ");

        if (value.getClass() == String.class) {
            str.append(String.format("\'%s\'", value));
        } else {
            str.append(value);
        }

        return str.toString();
    }
}