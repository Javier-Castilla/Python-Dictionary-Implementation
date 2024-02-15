package org.ulpgc.edp.model;

public class Dictionary {
    private Node[] entries;
    private Node lastItem;
    private int length;
    private int occupiedBoxes;

    /**
     * Constructor by default. No length or items needed.
     */
    public Dictionary() {
        this.entries = new Node[11];
    }

    /**
     * Constructor with initial length of the hash table specified.
     *
     * @param length of the initial hash table
     */
    public Dictionary(int length) {
        this.entries = new Node[nextPrimeNumber(length)];
    }

    /**
     * Constructor given a data structure of pairs or tuples key - value to put into the new dictionary.
     *
     * @param items to put into the new dictionary
     */
    public Dictionary(Object[][] items) {
        for (int index = 0; index < items.length; index++) {
            Object key = items[index][0];
            Object value = items[index][1];
            put(key, value);
        }
    }

    /**
     *
     * @return the number of elements in the dictionary
     */
    public int length() {
        return length;
    }

    /**
     * Private method which calculates the next prime number of the given one.
     *
     * @param num to calculate the next prime number
     * @return the next prime number
     */
    private int nextPrimeNumber(int num) {
        while (true) {
            int counter = 0;
            for (int i = 2; i < num; i++) {
                if (num % i == 0) {
                    num++;
                    counter++;
                    break;
                }
            }
            if (counter == 0) return num;
        }
    }

    public Object pop(Object key){
        int index = hash(key);
        Node nodo = entries[index];
        if(nodo.key().equals(key)){
            if(nodo.nextNode() == null) {
                entries[index] = null;
            } else {
                entries[index] = nodo.nextNode();
            }
            return nodo.value();
        }

        while(nodo.nextNode() != null && nodo.key() != key){
            nodo = nodo.nextNode();
        }

        if(nodo.nextNode() == null){
            nodo.prevNode().setnextNode(null);
        } else {
            nodo.prevNode().setnextNode(nodo.nextNode());
            nodo.nextNode().setPrevNode(nodo.prevNode());
        }

        if(nodo.nextIntroducedNode() == null){
            nodo.prevIntroducedNode().setNextIntroducedNode(null);
        } else {
            nodo.prevIntroducedNode().setNextIntroducedNode(nodo.nextIntroducedNode());
            nodo.nextIntroducedNode().setPrevIntroducedNode(nodo.prevIntroducedNode());
        }

        length -= 1;
        return nodo.value();
    }

    /**
     * Internal and private class used to store the given pairs key - value.
     * It has its own String representation.
     *
     */
    private class Node {
        private Object key;
        private Object value;
        private Node prevNode;
        private Node nextNode;
        private Node nextIntroducedNode;
        private Node prevIntroducedNode;
        private boolean isRoot;

        /**
         * Constructor of the Node Class.
         *
         * @param key
         * @param value
         * @param isRoot
         */
        private Node(Object key, Object value, boolean isRoot) {
            this.key = key;
            this.value = value;
            this.isRoot = isRoot;
            Dictionary.this.lastItem = this;
        }

        public Object key() {
            return key;
        }

        public Node setKey(Object key) {
            this.key = key;
            return this;
        }

        public Object value() {
            return value;
        }

        public Node setValue(Object value) {
            this.value = value;
            return this;
        }

        public Node prevNode() {
            return prevNode;
        }

        public Node setPrevNode(Node prevNode) {
            this.prevNode = prevNode;
            return this;
        }

        public Node nextNode() {
            return nextNode;
        }

        public Node setnextNode(Node nextNode) {
            this.nextNode = nextNode;
            return this;
        }

        public Node nextIntroducedNode() {
            return nextIntroducedNode;
        }

        public Node setNextIntroducedNode(Node nextIntroducedNode) {
            this.nextIntroducedNode = nextIntroducedNode;
            return this;
        }

        public Node prevIntroducedNode() {
            return prevIntroducedNode;
        }

        public Node setPrevIntroducedNode(Node prevIntroducedNode) {
            this.prevIntroducedNode = prevIntroducedNode;
            return this;
        }

        public boolean isRoot() {
            return isRoot;
        }

        public void setIsRoot(boolean root) {
            isRoot = root;
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
            Node node = (Node) object;
            return key.equals(node.key());
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
}
