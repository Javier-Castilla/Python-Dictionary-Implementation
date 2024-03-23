package org.ulpgc.edp.model.dct;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Iterable class used to iterate over the dictionary values.
 * This class represents a dynamic view of the dictionary values.
 *
 * @author Javier Castilla
 * @version 22-03-2024
 */
class DictionaryValuesIterable implements Iterable<Object> {
    private Dictionary dict;

    /**
     * Constructor of the iterable class given a reference of a dictionary.
     */
    DictionaryValuesIterable(Dictionary dict) {
        this.dict = dict;
    }

    /**
     * Returns the length of the values set of the dictionary.
     *
     * @return length of dynamic view
     */
    public int length() {
        return dict.size();
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
     * Private inner class used to iterate over the dictionary values.
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
         * Override method which returns if there is such a next element.
         *
         * @return true if it has next element else false
         */
        @Override
        public boolean hasNext() {
            return index < length && node != null && node.index() != -1;
        }

        /**
         * Method that returns the value iterating upon the items array.
         *
         * @return the next value
         * @exception NoSuchElementException when there are no more items to iterate
         */
        @Override
        public Object next() throws NoSuchElementException {
            if (hasNext()) {
                Object value = node.value();
                node = dict.entries()[++index];

                return value;
            }
            throw new NoSuchElementException();
        }
    }

    /**
     * Compares a given object with the current dictionary values iterable,
     * checking equality between them.
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
     * String representation of the Iterable class.
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
                str.append(String.format("'%s', ", value));
            } else {
                str.append(value + ", ");
            }
        }

        if (dict.size() != 0) {
            str.setLength(str.length() - 2);
        }

        str.append("])");

        return str.toString();
    }
}