package org.ulpgc.edp.model;

import java.util.Iterator;

/**
 * Iterable class used to iterate over the Dictionary Keys.
 */
class DictionaryKeysIterator implements Iterable<Object> {
    private Integer[] indexes;
    private Node[] items;
    private Dictionary dict;

    /**
     * Constructor of the iterable class.
     */
    DictionaryKeysIterator(Integer[] indexes, Node[] items, Dictionary dict) {
        this.indexes = indexes;
        this.items = items;
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
            this.length = items.length;
            this.node = items[index];
        }
        /**
         * Overrided method which returns if there is a next element or not.
         *
         * @return true if has next element else false
         */
        @Override
        public boolean hasNext() {
            return index < length && node != null && indexes[node.index()] != -1;
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
                node = items[++index];
                return key;
            }
            throw new java.util.NoSuchElementException();
        }
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
