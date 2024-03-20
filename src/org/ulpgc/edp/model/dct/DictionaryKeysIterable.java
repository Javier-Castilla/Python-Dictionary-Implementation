package org.ulpgc.edp.model.dct;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;

/**
 * Iterable class used to iterate over the dictionary keys.
 * This class represents a dynamic view of the dictionary keys.
 *
 * @author Javier Castilla
 * @version 15-03-2024
 */
class DictionaryKeysIterable implements Iterable<Object> {
    private Dictionary dict;

    /**
     * Constructor of the iterable class given a reference of a dictionary.
     */
    DictionaryKeysIterable(Dictionary dict) {
        this.dict = dict;
    }

    /**
     * Iterator method.
     *
     * @return an iterator
     */
    @Override
    public Iterator<Object> iterator() {
        return new DictionaryKeys();
    }

    /**
     * Private inner class used to iterate over the dictionary keys.
     */
    private class DictionaryKeys implements Iterator<Object> {
        private int index, length;
        private Node node;

        private DictionaryKeys() {
            this.index = 0;
            this.length = dict.entries().length;
            this.node = dict.entries()[index];
        }
        /**
         * Override method which returns if there is such a next element.
         *
         * @return true if it has next element else false
         */
        @Override
        public boolean hasNext() {
            return index < length && node != null && dict.indexes()[node.index()] != -1;
        }

        /**
         * Method that returns the key iterating upon the items array.
         *
         * @return the next key
         */
        @Override
        public Object next() {
            if (hasNext()) {
                Object key = node.key();
                node = dict.entries()[++index];
                return key;
            }
            throw new java.util.NoSuchElementException();
        }
    }

    /**
     * Compares a given object with the current dictionary keys iterable,
     * checking equality between them.
     *
     * @param object to compare
     * @return true if equals else false
     */
    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        DictionaryKeysIterable other = (DictionaryKeysIterable) object;
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
        str.append("DictionaryKeys([");

        for (Object key : this) {
            if (key.getClass() == String.class) {
                str.append(String.format("'%s'", key));
            } else {
                str.append(key);
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
