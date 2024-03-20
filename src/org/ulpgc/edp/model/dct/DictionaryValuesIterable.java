package org.ulpgc.edp.model.dct;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;

/**
 * Iterable class used to iterate over the dictionary's values.
 *
 * @author Javier Castilla
 * @version 15-03-2024
 */
class DictionaryValuesIterable implements Iterable<Object> {
    private Dictionary dict;

    /**
     * Constructor of the iterable class.
     */
    DictionaryValuesIterable(Dictionary dict) {
        this.dict = dict;
    }

    /**
     * Iterator method.
     *
     * @return an iterator
     */
    @Override
    public Iterator<Object> iterator() {
        return new DictionaryValues();
    }

    /**
     * Private inner class used to iterate over the dictionary's values.
     */
    private class DictionaryValues implements Iterator<Object> {
        private int index, length;
        private Node node;

        private DictionaryValues() {
            this.index = 0;
            this.length = dict.entries().length;
            this.node = dict.entries()[index];
        }

        /**
         * Override method which returns if there is a next element or not.
         *
         * @return true if it has next element else false
         */
        @Override
        public boolean hasNext() {
            return index < length && node != null && dict.indexes()[node.index()] != -1;
        }

        /**
         * Method that returns the value iterating upon the items array.
         *
         * @return the next value
         */
        @Override
        public Object next() {
            if (hasNext()) {
                Object value = node.value();
                node = dict.entries()[++index];

                return value;
            }
            throw new java.util.NoSuchElementException();
        }
    }

    /**
     * Override method that compares a given object with the current one.
     *
     * @param object to compare
     * @return true if equals else false
     */
    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        DictionaryValuesIterable other = (DictionaryValuesIterable) object;
        return toString().equals(other.toString());
    }

    /**
     * Override hashCode method that returns an integer representation
     * of the object.
     *
     * @return an integer representation
     */
    @Override
    public int hashCode() {
        int result = Objects.hash(dict);
        result = 11 * result + Arrays.hashCode(dict.indexes());
        result = 11 * result + Arrays.hashCode(dict.entries());
        return result;
    }

    /**
     * String representation of the Iterable class
     *
     * @return a string representation
     */
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("DictionaryValues([");

        for (Object value : this) {
            System.out.println(value);
            if (value.getClass() == String.class) {
                str.append(String.format("\'%s\'", value));
            } else {
                str.append(value);
            }
            str.append(", ");
        }

        if (dict.size() != 0) {
            str.setLength(str.length() - 2);
        }

        str.append("])");

        return str.toString();
    }
}