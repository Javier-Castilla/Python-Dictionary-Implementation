package org.ulpgc.edp.model.dct;

/**
 * Node class used to storage a pair key - value and its index of and indexes
 * hash table.
 *
 * @author Javier Castilla
 * @version 15-03-2024
 */
class Node {
    private Object key, value;
    private int index;

    /**
     * Constructor of the node class given a key, value and index.
     *
     * @param key of the inserted pair
     * @param value of the inserted pair
     * @param index where current reference index is stores
     */
    Node(Object key, Object value, int index) {
        this.key = key;
        this.value = value;
        this.index = index;
    }

    /**
     * Getter for key attribute.
     *
     * @return key
     */
    Object key() {
        return key;
    }

    /**
     *  Package level setter for key attribute.
     *
     * @param key to store
     */
    void key(Object key) {
        this.key = key;
    }

    /**
     * Package level getter for value attribute.
     *
     * @return value
     */
    Object value() {
        return value;
    }

    /**
     * Package level setter for value attribute.
     *
     * @param value to store
     */
    void value(Object value) {
        this.value = value;
    }

    /**
     * Package level getter for index attribute.
     *
     * @return index
     */
    int index() {
        return index;
    }

    /**
     * Package level setter for index value.
     *
     * @param index to store
     */
    void index(int index) {
        this.index = index;
    }

    /**
     * String representation of the Node class.
     *
     * @return a string representation
     */
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append(format(key) + ": ");
        str.append(format(value));

        return str.toString();
    }

    /**
     * Private inner method used to format the items of the current node in
     * order to make the string representation.
     *
     * @param item to format
     * @return formatted item
     */
    private String format(Object item) {
        if (item == null) {
            return "null";
        } else if (item.getClass() == String.class) {
            return String.format("'%s'", item);
        } else {
            return item.toString();
        }
    }
}