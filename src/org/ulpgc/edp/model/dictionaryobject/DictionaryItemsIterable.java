package org.ulpgc.edp.model.dictionaryobject;

import org.ulpgc.edp.model.tupleobject.*;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;

/**
 * Iterable class used to iterate over the dictionary's items.
 *
 * @author Javier Castilla
 * @version 15-03-2024
 */
class DictionaryItemsIterable implements Iterable<Tuple> {
    private Dictionary dict;

    /**
     * Constructor of the iterable class.
     */
    DictionaryItemsIterable(Dictionary dict) {
        this.dict = dict;
    }

    /**
     * Iterator method.
     *
     * @return an iterator
     */
    @Override
    public Iterator<Tuple> iterator() {
        return new DictionaryItems();
    }

    /**
     * Private inner class used to iterate over the dictionary's items.
     */
    private class DictionaryItems implements Iterator<Tuple> {
        private int index, length;
        private Node node;

        private DictionaryItems() {
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
         * Method that returns the entry iterating upon the items array.
         *
         * @return the next pair
         */
        @Override
        public Tuple next() {
            if (hasNext()) {
                Tuple returnedItems = new Tuple(node.key(), node.value());
                node = dict.entries()[++index];

                return returnedItems;
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
        DictionaryItemsIterable other = (DictionaryItemsIterable) object;
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
     * String representation of the iterable class
     *
     * @return a string representation
     */
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("DictionaryItems([");

        for (Tuple item : this) {
            str.append(item);
            str.append(", ");
        }

        if (dict.size() != 0) {
            str.setLength(str.length() - 2);
        }

        str.append("])");

        return str.toString();
    }
}