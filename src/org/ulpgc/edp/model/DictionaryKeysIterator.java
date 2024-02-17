package org.ulpgc.edp.model;

import java.util.Iterator;

public class DictionaryKeysIterator implements Iterable<String> {
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
    public Iterator<String> iterator() {
        return new DictionaryKeys();
    }

    private class DictionaryKeys implements Iterator<String> {
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
        public String next() {
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

        for (String key : this) {
            str.append("'").append(key).append("'").append(", ");
        }

        if (dictionary.length() != 0) {
            str.setLength(str.length() - 2);
        }

        str.append(")");

        return str.toString();
    }
}
