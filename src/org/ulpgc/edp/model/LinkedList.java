package org.ulpgc.edp.model;

public class LinkedList {
    private Node fristNode, lastNode;
    private int length;

    void append(Object key, Object value) {
        Node newNode = new Node(key, value);
        lastNode.nextNode = newNode;
        lastNode = newNode;
    }

    void pop(Object key) {
        Node current = fristNode;

        while (current.nextNode != null && !current.key.equals(key)) {
            current = current.nextNode;
        }

        current.prevNode = current.nextNode;
    }
}
