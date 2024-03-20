package org.ulpgc.edp.model.tpl;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Iterable class used to iterate over the Tuple Items.
 * This class represents a dynamic view of the tuple items.
 *
 * @author Javier Castilla
 * @version 15-03-2024
 */
class TupleItemsIterable implements Iterable<Object> {
    private Tuple tuple;

    /**
     * Constructor of the iterable class.
     *
     * @param tuple
     */
    public TupleItemsIterable(Tuple tuple) {
        this.tuple = tuple;
    }

    /**
     * Override iterator method.
     *
     * @return an iterator
     */
    @Override
    public Iterator<Object> iterator() {
        return new TupleItemsIterator();
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
            return index < tuple.length() && tuple.items()[index] != null;
        }

        /**
         * Method that returns the item iterating upon the items array of the tuple.
         *
         * @return the next item
         */
        @Override
        public Object next() {
            if (hasNext()) {
                Object item = tuple.items()[index];
                index++;
                return item;
            }
            throw new NoSuchElementException();
        }
    }
}
