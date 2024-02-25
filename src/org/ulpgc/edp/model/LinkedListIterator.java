package org.ulpgc.edp.model;

import java.util.Iterator;

class LinkedListIterator implements Iterable<LinkedList.Node> {
    private LinkedList list;
    private LinkedList.Node current;

    LinkedListIterator(LinkedList list) {
        this.list = list;
        this.current = list.firstNode();
    }

    @Override
    public Iterator<LinkedList.Node> iterator() {
        return new LinkedListItems();
    }

    private class LinkedListItems implements Iterator<LinkedList.Node> {
        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public LinkedList.Node next() {
            if (hasNext()) {
                LinkedList.Node node = current;
                current = current.nextNode();
                return node;
            }
            throw new java.util.NoSuchElementException();
        }
    }
}
