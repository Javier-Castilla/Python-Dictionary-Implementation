package org.ulpgc.edp.model.tpl;

import org.ulpgc.edp.exceptions.IndexError;
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Tuple class used to storage elements, not allowing editing the data structure
 * once it is created.
 *
 * @author Javier Castilla
 * @version 28-03-2024
 */
public class Tuple implements Iterable<Object> {
    private final Object[] items;
    private int size;

    /**
     * Constructor given dynamically all the items wanted to be into the tuple
     * or items into an array.
     *
     * @param items to add into de tuple
     */
    public Tuple(Object... items) {
        this(
                () -> new Iterator<>() {
                    private int index = 0;

                    @Override
                    public boolean hasNext() {
                        return index < items.length;
                    }

                    @Override
                    public Object next() {
                        if (hasNext()) {
                            return items[index++];
                        }
                        throw new NoSuchElementException();
                    }
                }
        );
    }

    /**
     * Constructor given an iterable to build the new tuple. The elements
     * contained into that iterable must be tuples.
     *
     * @param items
     */
    public Tuple(Iterable<?> items) {
        this.items = new Object[getSuitableLength(items)];

        int index = 0;
        for (Object item : items) {
            this.items[index++] = item;
            size++;
        }
    }

    /**
     * Calculates the length of the given iterable.
     *
     * @param iterable to calculate length
     * @return iterable length
     */
    private int getSuitableLength(Iterable<?> iterable) {
        int length = 0;
        for (Object ignored : iterable) length++;
        return length;
    }

    /**
     * Tuple items package level getter.
     *
     * @return tuple items reference
     */
    Object[] items() {
        return items;
    }

    /**
     * Returns the tuple length.
     *
     * @return tuple length
     */
    public int size() {
        return size;
    }

    /**
     * Returns the element located in the specified index.
     *
     * @param index of the item
     * @return item at given index
     * @exception IndexError if index is not suitable
     */
    public Object get(int index) throws IndexError {
        if (size == 0 || index < 0 || index >= size) {
            throw new IndexError("tuple index out of range");
        }
        return items[index];
    }

    /**
     * Override method used to iterate over the tuple.
     *
     * @return an iterator of the Tuple class
     */
    @Override
    public Iterator<Object> iterator() {
        return new TupleItemsIterator();
    }

    /**
     * Compares a given object with the current one, checking equality between them.
     *
     * @param object to compare
     * @return true if objects are equals else false
     */
    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Tuple other = (Tuple) object;
        if (size() != other.size()) return false;

        Iterator<Object> it1 = iterator();
        Iterator<Object> it2 = other.iterator();

        while (it1.hasNext() && it2.hasNext()) {
            Object item1 = it1.next();
            Object item2 = it2.next();

            if (!item1.equals(item2)) return false;
        }

        return true;
    }

    /**
     * Override method that returns the hashCode of the instanced object.
     *
     * @return hashCode of the instance object
     */
    @Override
    public int hashCode() {
        return Arrays.hashCode(items);
    }

    /**
     * Override method that gives the string representation of the object.
     *
     * @return the string representation
     */
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("(");

        for (Object item : items) {
            if (item == null) {
                str.append("null, ");
            } else if (item.getClass() == String.class) {
                str.append(String.format("'%s', ", item));
            } else {
                str.append(item).append(", ");
            }
        }

        if (items.length > 0) {
            str.setLength(str.length() - 2);
        }

        str.append(")");

        return str.toString();
    }

    /**
     * Inner private class used to iterate over the tuple items.
     */
    private class TupleItemsIterator implements Iterator<Object> {
        private int index;

        public TupleItemsIterator() {
            this.index = 0;
        }

        /**
         * Override method which returns if there is such a next element.
         *
         * @return true if it has next element else false
         */
        @Override
        public boolean hasNext() {
            return index < size() && items()[index] != null;
        }

        /**
         * Method that returns the item iterating upon the items array of the tuple.
         *
         * @return the next item
         * @exception NoSuchElementException when there are no more items to iterate
         */
        @Override
        public Object next() throws NoSuchElementException {
            if (hasNext()) {
                Object item = items()[index];
                index++;
                return item;
            }
            throw new NoSuchElementException();
        }
    }
}
