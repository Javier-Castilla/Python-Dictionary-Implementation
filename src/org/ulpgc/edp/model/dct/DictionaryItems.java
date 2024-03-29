package org.ulpgc.edp.model.dct;

import org.ulpgc.edp.flags.Flags;
import org.ulpgc.edp.model.tpl.*;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Iterable class used to iterate over the dictionary items.
 * This class represents a dynamic view of dictionary items.
 *
 * @author Javier Castilla
 * @version 28-03-2024
 */
public class DictionaryItems implements Iterable<Tuple> {
    private static final int REMOVED = -1;
    private static final Flags NONE = Flags.NONE;
    private final Dictionary dict;
    private boolean isReversed;

    /**
     * Constructor of the iterable class given the reference of the dictionary.
     */
    DictionaryItems(Dictionary dict) {
        this.dict = dict;
        this.isReversed = false;
    }

    /**
     * Returns the length of the items set of the dictionary.
     *
     * @return length of dynamic view
     */
    public int size() {
        return dict.size();
    }

    /**
     * Checks if given iterable and current dictionary items set are disjoint.
     * Given values must be a Tuples, otherwise result will always be false.
     *
     * @param other to compare
     * @return true if are disjoint else false
     */
    public boolean isDisjoint(Iterable<?> other) {
        for (Object item : other) {
            if (item.getClass() == Tuple.class) {
                Tuple pair = (Tuple) item;
                if (pair.size() != 2) continue;
                Object value = dict.get(pair.get(0), NONE);
                Object otherValue = pair.get(1);
                if (compareEquality(value, otherValue)) return false;
            }
        }

        return true;
    }

    /**
     * Checks if given tuple is contained into dictionary items.
     *
     * @param tuple to check
     * @return true it given tuple is contained else false
     */
    public boolean contains(Tuple tuple) {
        if (tuple == null || tuple.size() != 2) return false;
        Object value = dict.get(tuple.get(0), NONE);
        Object otherValue = tuple.get(1);
        return compareEquality(value, otherValue);
    }

    /**
     * Private method that checks equality between two given values.
     *
     * @param value to compare
     * @param otherValue to compare
     * @return true if equals else false
     */
    private boolean compareEquality(Object value, Object otherValue) {
        return value != NONE && (Objects.equals(value, otherValue));
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
    public DictionaryItems reverse() {
        this.isReversed = !isReversed;
        return this;
    }

    /**
     * Iterator method.
     *
     * @return an iterator
     */
    @Override
    public Iterator<Tuple> iterator() {
        return (isReversed) ? new DictionaryItemsReversedIterator()
                : new DictionaryItemsIterator();
    }

    /**
     * Private inner class used to iterate over the dictionary items.
     */
    private class DictionaryItemsIterator implements Iterator<Tuple> {
        private int index;
        private final int length;
        private Node node;

        private DictionaryItemsIterator() {
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
            throw new NoSuchElementException();
        }
    }

    /**
     * Private inner class used to reverse iterate over the dictionary items.
     */
    private class DictionaryItemsReversedIterator implements Iterator<Tuple> {
        private int index;
        private Node node;

        private DictionaryItemsReversedIterator() {
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
         * Method that returns the entry iterating upon the items array.
         *
         * @return the next pair
         */
        @Override
        public Tuple next() {
            if (hasNext()) {
                Tuple returnedItems = new Tuple(node.key(), node.value());
                index--;

                if (index < 0) {
                    node = null;
                } else {
                    node = dict.entries()[index];
                }

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
        org.ulpgc.edp.model.dct.DictionaryItems other = (org.ulpgc.edp.model.dct.DictionaryItems) object;
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
        for (Tuple item : this) {
            result += 11 * item.hashCode();
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
        str.append("DictionaryItems([");

        for (Tuple item : this) {
            str.append(item).append(", ");
        }

        if (dict.size() != 0) {
            str.setLength(str.length() - 2);
        }

        str.append("])");

        return str.toString();
    }
}