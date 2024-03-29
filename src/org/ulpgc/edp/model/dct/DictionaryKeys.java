package org.ulpgc.edp.model.dct;

import org.ulpgc.edp.flags.Flags;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Iterable class used to iterate over the dictionary keys.
 * This class represents a dynamic view of the dictionary keys.
 *
 * @author Javier Castilla
 * @version 28-03-2024
 */
public class DictionaryKeys implements Iterable<Object> {
    private static final int REMOVED = -1;
    private static final Flags NONE = Flags.NONE;
    private final Dictionary dict;
    private boolean isReversed;

    /**
     * Constructor of the iterable class given a reference of a dictionary.
     */
    DictionaryKeys(Dictionary dict) {
        this.dict = dict;
        this.isReversed = false;
    }

    /**
     * Returns the length of the keys set of the dictionary.
     *
     * @return length of dynamic view
     */
    public int size() {
        return dict.size();
    }

    /**
     * Checks if given iterable and current dictionary items set are disjoint.
     *
     * @param other to compare
     * @return true if are disjoint else false
     */
    public boolean isDisjoint(Iterable<?> other) {
        for (Object item : other) {
            if (dict.get(item, NONE) != NONE) return false;
        }

        return true;
    }

    /**
     * Checks if given value is contained into dictionary keys.
     *
     * @param value to check
     * @return true it given value is contained else false
     */
    public boolean contains(Object value) {
        return dict.get(value, NONE) != NONE;
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
    public DictionaryKeys reverse() {
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
        return (isReversed) ? new DictionaryKeysReversedIterator()
                : new DictionaryKeysIterator();
    }

    /**
     * Private inner class used to iterate over the dictionary keys.
     */
    private class DictionaryKeysIterator implements Iterator<Object> {
        private int index;
        private final int length;
        private Node node;

        private DictionaryKeysIterator() {
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
         * Method that returns the key iterating upon the items array.
         *
         * @return the next key
         * @exception NoSuchElementException when there are no more items to iterate
         */
        @Override
        public Object next() throws NoSuchElementException {
            if (hasNext()) {
                Object key = node.key();
                node = dict.entries()[++index];
                return key;
            }
            throw new NoSuchElementException();
        }
    }

    /**
     * Private inner class used to reverse iterate over the dictionary keys.
     */
    private class DictionaryKeysReversedIterator implements Iterator<Object> {
        private int index;
        private Node node;

        private DictionaryKeysReversedIterator() {
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
         * Method that returns the key iterating upon the items array.
         *
         * @return the next key
         * @exception NoSuchElementException when there are no more items to iterate
         */
        @Override
        public Object next() throws NoSuchElementException {
            if (hasNext()) {
                Object key = node.key();
                index--;

                if (index < 0) {
                    node = null;
                } else {
                    node = dict.entries()[index];
                }

                return key;
            }
            throw new NoSuchElementException();
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
        org.ulpgc.edp.model.dct.DictionaryKeys other = (org.ulpgc.edp.model.dct.DictionaryKeys) object;
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
        for (Object key : this) {
            result += 11 * key.hashCode();
        }
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
        str.append("DictionaryKeys([");

        for (Object key : this) {
            if (key.getClass() == String.class) {
                str.append(String.format("'%s', ", key));
            } else {
                str.append(key).append(", ");
            }
        }

        if (dict.size() != 0) {
            str.setLength(str.length() - 2);
        }

        str.append("])");

        return str.toString();
    }
}
