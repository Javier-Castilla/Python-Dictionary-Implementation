package org.ulpgc.edp.model.dct;

import java.util.Arrays;
import java.util.Objects;

/**
 * Node class used to storage a pair key - value and its index of and indexes
 * hash table.
 *
 * @author Javier Castilla
 * @version 28-03-2024
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
     * Compares another object with the current one.
     *
     * @param object to compare
     * @return true if objects are equal else false
     */
    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Node node = (Node) object;
        return Objects.equals(key, node.key) && Objects.equals(value, node.value);
    }

    /**
     * Calculates and returns the hashCode value.
     *
     * @return hashCode value
     */
    @Override
    public int hashCode() {
        return Objects.hash(key, value, index);
    }

    /**
     * String representation of the Node class.
     *
     * @return a string representation
     */
    @Override
    public String toString() {
        return format(key) + ": " + format(value);
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
            return "None";
        } else if (item.getClass() == String.class) {
            return String.format("'%s'", item);
        } else {
            if (item instanceof Object[]) {
                return Arrays.toString((Object[]) item);
            }
            return item.toString();
        }
    }
}