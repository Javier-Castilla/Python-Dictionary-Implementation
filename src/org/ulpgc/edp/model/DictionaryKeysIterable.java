package org.ulpgc.edp.model;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;

/**
 * Iterable class used to iterate over the Dictionary Keys.
 *
 * @author Javier Castilla
 * @author David Miranda
 * @author Esteban Trujillo
 * @author Elena Artiles
 */
class DictionaryKeysIterable implements Iterable<Object> {
    private Dictionary dict;

    /**
     * Constructor of the iterable class.
     */
    DictionaryKeysIterable(Dictionary dict) {
        this.dict = dict;
    }

    /**
     * Iterator method.
     * @return an iterator
     */
    @Override
    public Iterator<Object> iterator() {
        return new DictionaryKeys();
    }

    /**
     * Private inner class used to iterate over the Dictionary keys.
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
         * Overrided method which returns if there is a next element or not.
         *
         * @return true if has next element else false
         */
        @Override
        public boolean hasNext() {
            return index < length && node != null && dict.indexes()[node.index()] != -1;
        }

        /**
         * Method that returns the node iterating upon the LinkedList.
         *
         * @return the next node
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
     * Overrided equals method that compares a given object with the current
     * one.
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
     * Overrided hashCode method that returns an integer representation
     * of the object.
     *
     * @return an integer representation
     */
    @Override
    public int hashCode() {
        int result = Objects.hash(dict);
        result = 31 * result + Arrays.hashCode(dict.indexes());
        result = 31 * result + Arrays.hashCode(dict.entries());
        return result;
    }

    /**
     * String representation of the Iterable class
     * @return a string representation
     */
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();

        str.append("DictionaryKeys([");

        for (Object key : this) {
            if (key.getClass() == String.class) {
                str.append(String.format("\'%s\'", key));
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
