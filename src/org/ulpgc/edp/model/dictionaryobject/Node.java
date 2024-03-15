package org.ulpgc.edp.model.dictionaryobject;

/**
 * Node class used to storage a pair key - value and its index of and indexes
 * hash table.
 *
 * @author Javier Castilla
 * @version 15-03-2024
 */
class Node {
    private Object key;
    private Object value;
    private int index;

    /**
     * Constructor of the node class.
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
     * Getter for key attribute.
     *
     * @return key
     */
    Object key() {
        return key;
    }

    /**
     * Setter for key attribute.
     *
     * @param key
     */
    void key(Object key) {
        this.key = key;
    }

    /**
     * Getter for value attribute.
     *
     * @return value
     */
    Object value() {
        return value;
    }

    /**
     * Setter for value attribute.
     *
     * @param value
     */
    void value(Object value) {
        this.value = value;
    }

    /**
     * Getter for index attribute.
     *
     * @return index
     */
    int index() {
        return index;
    }

    /**
     * Setter for index value.
     *
     * @param index
     */
    void index(int index) {
        this.index = index;
    }

    /**
     * Private inner method used to format the items of the current node in
     * order to make the string representation.
     *
     * @param item to format
     * @return formatted item
     */
    private String format(Object item) {
        if (item.getClass() == String.class) {
            return String.format("\'%s\'", item);
        } else {
            return item.toString();
        }
    }

    /**
     * String representation of the Node class.
     *
     * @return a string representation
     */
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append(format(key));
        str.append(": ");
        str.append(format(value));

        return str.toString();
    }
}