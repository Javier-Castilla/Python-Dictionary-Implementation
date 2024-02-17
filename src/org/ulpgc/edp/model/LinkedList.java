package org.ulpgc.edp.model;

import java.util.Iterator;

/**
 * LinkedList used to manage collisions in the HashTable.
 */
public class LinkedList {
    private LinkedList.Node firstNode, lastNode;
    private int length = 0;

    /**
     * Internal and private class used to store the given pairs key - value.
     * It has its own String representation.
     *
     */
    class Node {
        private Object key;
        private Object value;
        private LinkedList.Node prevNode;
        private LinkedList.Node nextNode;
        private LinkedList.Node nextIntroducedNode;
        private LinkedList.Node prevIntroducedNode;

        /**
         * Constructor of the Node Class.
         *
         * @param key
         * @param value
         */
        private Node(Object key, Object value) {
            this.key = key;
            this.value = value;
        }

        Object key() {
            return key;
        }

        Object value() {
            return value;
        }

        LinkedList.Node prevNode() {
            return prevNode;
        }

        LinkedList.Node nextNode() {
            return nextNode;
        }

        LinkedList.Node nextIntroducedNode() {
            return nextIntroducedNode;
        }

        void nextIntroducedNode(LinkedList.Node nextIntroducedNode) {
            this.nextIntroducedNode = nextIntroducedNode;
        }

        LinkedList.Node prevIntroducedNode() {
            return prevIntroducedNode;
        }

        void prevIntroducedNode(LinkedList.Node prevIntroducedNode) {
            this.prevIntroducedNode = prevIntroducedNode;
        }

        /**
         *
         * @param object
         * @return whether the given Nodes key is equals to the actual one or not
         */
        @Override
        public boolean equals(Object object) {
            if (this == object) return true;
            if (object == null || getClass() != object.getClass()) return false;
            LinkedList.Node node = (LinkedList.Node) object;
            return key.equals(node.key) && value.equals(node.value);
        }

        /**
         *
         * @return the representation of the Node Class
         */
        @Override
        public String toString() {
            StringBuilder str = new StringBuilder();
            str.append(
                    (key.getClass() == String.class) ? String.format("\'%s\'", key) : key
            );
            str.append(": ");
            str.append(
                    (value.getClass() == String.class) ? String.format("\'%s\'", value) : value
            );
            return str.toString();
        }
    }

    public Node firstNode() {
        return firstNode;
    }

    public Node lastNode() {
        return lastNode;
    }

    public int length() {
        return length;
    }

    /**
     * Appends a new node containing the given pair key - value.
     *
     * @param key to add to the new node
     * @param value to add to the new node
     * @param lastIntroducedNode of the dictionary
     * @return the added node
     */
    LinkedList.Node append(Object key, Object value, LinkedList.Node lastIntroducedNode) {
        LinkedList.Node newNode = new LinkedList.Node(key, value);

        if (length == 0) {
            firstNode = newNode;
            lastNode = newNode;
            if (lastIntroducedNode != null) {
                lastIntroducedNode.nextIntroducedNode = newNode;
            }
            newNode.prevNode = lastNode;
            newNode.prevIntroducedNode = lastIntroducedNode;
            length++;
            return newNode;
        }

        LinkedList.Node current = firstNode;

        while (current.nextNode != null && !current.key.equals(key)) {
            current = current.nextNode;
        }

        if (current.nextNode == null) {
            if (current.key.equals(key)) {
                current.value = value;
                newNode = null;
            } else {
                current.nextNode = newNode;
                if (lastIntroducedNode != null) {
                    System.out.println(lastIntroducedNode.nextIntroducedNode);
                    lastIntroducedNode.nextIntroducedNode = newNode;
                }
                newNode.prevNode = lastNode;
                newNode.prevIntroducedNode = lastIntroducedNode;
                lastNode = newNode;
                length++;
            }
        } else {
            current.value = value;
            newNode = null;
        }

        return newNode;
    }

    /**
     * Pops the node with the given key.
     *
     * @param key to remove
     * @return the removed node
     */
    LinkedList.Node pop(Object key) {
        if (length == 0) {
            return null;
        }

        LinkedList.Node current = firstNode;

        while (current.nextNode != null && !current.key.equals(key)) {
            current = current.nextNode;
        }

        if (current.prevNode != null) {
            current.prevNode.nextNode = current.nextNode;
        }

        if (current.prevIntroducedNode != null) {
            current.prevIntroducedNode.nextIntroducedNode = current.nextIntroducedNode;
        }

        if (current.nextNode != null) {
            current.nextNode.prevNode = current.prevNode;
        }

        if (current.nextIntroducedNode != null) {
            current.nextIntroducedNode.prevIntroducedNode = current.prevIntroducedNode;
        }

        if (length == 1) {
            firstNode = null;
            lastNode = null;
        } else {
            if (firstNode.key.equals(key)) {
                firstNode = current.nextNode;
            }
            lastNode = current.prevNode;
        }

        length--;
        return current;
    }

    /**
     * Looks for the node with the given key and returns it.
     *
     * @param key to look for
     * @return the node with the given key, null if the node is not present
     */
    LinkedList.Node get(Object key) {
        LinkedList.Node current = firstNode;

        while (current.nextNode != null && !current.key.equals(key)) {
            current = current.nextNode;
        }

        return (current.key.equals(key)) ? current : null;
    }

    /**
     * Compares another object with the current LinkedList
     *
     * @param object to compare
     * @return true if the objects are equal else false
     */
    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        LinkedList other = (LinkedList) object;

        if (length != other.length) return false;

        LinkedList.Node currentNode = firstNode;
        LinkedList.Node otherNode = other.firstNode;

        while (currentNode.nextNode != null && otherNode.nextNode != null) {
            if (!currentNode.equals(otherNode)) return false;
            currentNode = currentNode.nextNode;
            otherNode = otherNode.nextNode;
        }

        return true;
    }

    /**
     * Return the string representation of the object.
     *
     * @return string representatuion of the object
     */
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();

        LinkedList.Node current = firstNode;

        while (current != null) {
            str.append(current);
            if (current.nextNode != null) {
                str.append(", ");
            }
            current = current.nextNode;
        }

        return str.toString();
    }
}
