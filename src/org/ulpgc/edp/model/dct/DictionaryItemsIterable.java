package org.ulpgc.edp.model.dct;

import org.ulpgc.edp.model.tpl.*;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Iterable class used to iterate over the dictionary items.
 * This class represents a dynamic view of dictionary items.
 *
 * @author Javier Castilla
 * @version 22-03-2024
 */
class DictionaryItemsIterable implements Iterable<Tuple> {
    private Dictionary dict;

    /**
     * Constructor of the iterable class given the reference of the dictionary.
     */
    DictionaryItemsIterable(Dictionary dict) {
        this.dict = dict;
    }

    /**
     * Returns the length of the items set of the dictionary.
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
    public Iterator<Tuple> iterator() {
        return new DictionaryItems();
    }

    /**
     * Private inner class used to iterate over the dictionary items.
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
         * Override method which returns if there is such a next element.
         *
         * @return true if it has next element else false
         */
        @Override
        public boolean hasNext() {
            return index < length && node != null && node.index() != -1;
        }

        /**
         * Method that returns the entry iterating upon the items array.
         *
         * @return the next pair
         * @exception NoSuchElementException when there are no more items to iterate
         */
        @Override
        public Tuple next() throws NoSuchElementException {
            if (hasNext()) {
                Tuple returnedItems = new Tuple(node.key(), node.value());
                node = dict.entries()[++index];

                return returnedItems;
            }
            throw new NoSuchElementException();
        }
    }

    /**
     * Compares a given object with the current dictionary items iterable,
     * checking equality between them.
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
     * String representation of the iterable class.
     *
     * @return a string representation
     */
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("DictionaryItems([");

        for (Tuple item : this) {
            str.append(item + ", ");
        }

        if (dict.size() != 0) {
            str.setLength(str.length() - 2);
        }

        str.append("])");

        return str.toString();
    }
}