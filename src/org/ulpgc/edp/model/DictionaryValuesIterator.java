package org.ulpgc.edp.model;

import org.ulpgc.edp.model.Dictionary;
import org.ulpgc.edp.model.LinkedList;

import java.util.Iterator;

public class DictionaryValuesIterator implements Iterable<Object> {
    private Dictionary dictionary;
    private LinkedList.Node current;

    /**
     * Constructor of the iterator class.
     */
    public DictionaryValuesIterator(Dictionary dictionary, LinkedList.Node firstNode) {
        this.dictionary = dictionary;
        this.current = firstNode;
    }

    @Override
    public Iterator<Object> iterator() {
        return new DictionaryValuesIterator.DictionaryValues();
    }

    private class DictionaryValues implements Iterator<Object> {
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
                return node.value();
            }
            throw new java.util.NoSuchElementException();
        }
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();

        str.append("DictionaryValues([");

        for (Object value : this) {
            System.out.println(value);
            if (value.getClass() == String.class) {
                str.append(String.format("\'%s\'", value));
            } else {
                str.append(value);
            }
            str.append(", ");
        }

        if (dictionary.length() != 0) {
            str.setLength(str.length() - 2);
        }

        str.append("])");

        return str.toString();
    }
}