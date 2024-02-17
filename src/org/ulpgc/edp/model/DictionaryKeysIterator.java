package org.ulpgc.edp.model;

import java.util.Iterator;

public class DictionaryKeysIterator implements Iterable<Object> {
    private Dictionary dictionary;
    private LinkedList.Node current;

    /**
     * Constructor of the iterator class.
     */
    public DictionaryKeysIterator(Dictionary dictionary, LinkedList.Node firstNode) {
        this.dictionary = dictionary;
        this.current = firstNode;
    }

    @Override
    public Iterator<Object> iterator() {
        return new DictionaryKeys();
    }

    private class DictionaryKeys implements Iterator<Object> {
        /**
         * Overrided method which returns if there is a next element or not.
         *
         * @return true if has next element else false
         */
        @Override
        public boolean hasNext() {
            return current != null;
        }

        /**
         * Method that returns the node iterating upon the LinkedList.
         *
         * @return the next node
         */
        @Override
        public Object next() {
            if (hasNext()) {
                LinkedList.Node node = current;
                current = current.nextIntroducedNode();
                return node.key();
            }
            throw new java.util.NoSuchElementException();
        }
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();

        str.append("(");

        for (Object key : this) {
            str.append(
                    (key.getClass() == String.class) ?
                    String.format("\'%b\'", key) : key
            );
        }

        if (dictionary.length() != 0) {
            str.setLength(str.length() - 2);
        }

        str.append(")");

        return str.toString();
    }
}
