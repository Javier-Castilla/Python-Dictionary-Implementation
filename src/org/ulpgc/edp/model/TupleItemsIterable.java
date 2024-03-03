package org.ulpgc.edp.model;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class TupleItemsIterable implements Iterable<Object> {
    private Tuple tuple;
    public TupleItemsIterable(Tuple tuple) {
        this.tuple = tuple;
    }

    @Override
    public Iterator<Object> iterator() {
        return new TupleItemsIterator();
    }

    private class TupleItemsIterator implements Iterator<Object> {
        private int index;

        public TupleItemsIterator() {
            this.index = 0;
        }

        @Override
        public boolean hasNext() {
            return index < tuple.items().length && tuple.items()[index] != null;
        }

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
