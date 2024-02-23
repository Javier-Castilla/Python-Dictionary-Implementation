package org.ulpgc.edp.model;

public class MyNode {
    private Object key;
    private Object value;

    /**
     * Constructor of the Node Class.
     *
     * @param key
     * @param value
     */
    MyNode(Object key, Object value) {
        this.key = key;
        this.value = value;
    }

    public Object key() {
        return key;
    }

    public void key(Object key) {
        this.key = key;
    }

    public Object value() {
        return value;
    }

    public void value(Object value) {
        this.value = value;
    }

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