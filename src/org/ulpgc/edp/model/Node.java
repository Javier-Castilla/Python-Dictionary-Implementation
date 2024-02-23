package org.ulpgc.edp.model;

/**
 * Node class used to storage a pair key - value and its index of and indexes
 * hash table.
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
     */
    Node(Object key, Object value, int index) {
        this.key = key;
        this.value = value;
        this.index = index;
    }

    Object key() {
        return key;
    }

    void key(Object key) {
        this.key = key;
    }

    Object value() {
        return value;
    }

    void value(Object value) {
        this.value = value;
    }

    int index() {
        return index;
    }

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