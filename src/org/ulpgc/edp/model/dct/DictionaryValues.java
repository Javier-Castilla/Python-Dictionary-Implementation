package org.ulpgc.edp.model.dct;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Iterable class used to iterate over the dictionary values.
 * This class represents a dynamic view of the dictionary values.
 *
 * @author Javier Castilla
 * @see Dictionary
 * @version 28-03-2024
 */
public class DictionaryValues implements Iterable<Object> {
    private static final int REMOVED = -1;
    private final Dictionary dict;
    private boolean isReversed;

    /**
     * Constructor of the iterable class given a reference of a dictionary.
     */
    DictionaryValues(Dictionary dict) {
        this.dict = dict;
        this.isReversed = false;
    }

    /**
     * Returns the length of the values set of the dictionary.
     *
     * @return length of dynamic view
     */
    public int size() {
        return dict.size();
    }

    /**
     * Checks if given value is contained into dictionary keys.
     *
     * @param value to check
     * @return true it given value is contained else false
     */
    public boolean contains(Object value) {
        for (Object dictValue : this) {
            if (Objects.equals(value, dictValue)) return true;
        }

        return false;
    }

    /**
     * Tells if the current object is reversed or not.
     *
     * @return true if reversed else false
     */
    public boolean isReversed() {
        return isReversed;
    }

    /**
     * Reverse the current iterable object.
     *
     * @return this object
     */
    public DictionaryValues reverse() {
        this.isReversed = !isReversed;
        return this;
    }

    /**
     * Iterator method.
     *
     * @return an iterator
     */
    @Override
    public Iterator<Object> iterator() {
        return (isReversed) ? new DictionaryValuesReversedIterator()
                : new DictionaryValuesIterator();
    }

    /**
     * Private inner class used to iterate over the dictionary values.
     */
    private class DictionaryValuesIterator implements Iterator<Object> {
        private int index;
        private final int length;
        private Node node;

        private DictionaryValuesIterator() {
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
            return index < length && node != null && node.index() != REMOVED;
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
     * Private inner class used to reverse iterate over the dictionary values.
     */
    private class DictionaryValuesReversedIterator implements Iterator<Object> {
        private int index;
        private Node node;

        private DictionaryValuesReversedIterator() {
            this.index = dict.getLastIndex();
            this.node = dict.entries()[index];
        }

        /**
         * Override method which returns if there is such a next element.
         *
         * @return true if it has next element else false
         */
        @Override
        public boolean hasNext() {
            return index >= 0 && node != null && node.index() != REMOVED;
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
                index--;

                if (index < 0) {
                    node = null;
                } else {
                    node = dict.entries()[index];
                }

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
        DictionaryValues other = (DictionaryValues) object;
        if (size() != other.size()) return false;
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
        int result = 0;
        for (Object value : this) {
            result += 11 * value.hashCode();
        }
        return result;
    }

    /**
     * String representation of the iterable class.
     *
     * @return a string representation
     */
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("DictionaryValues([");

        for (Object value : this) {
            if (value.getClass() == String.class) {
                str.append(String.format("'%s', ", value));
            } else {
                str.append(value).append(", ");
            }
        }

        if (dict.size() != 0) {
            str.setLength(str.length() - 2);
        }

        str.append("])");

        return str.toString();
    }
}