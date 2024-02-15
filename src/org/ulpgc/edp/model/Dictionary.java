package org.ulpgc.edp.model;

public class Dictionary {
    private LinkedList.Node[] entries;
    private LinkedList.Node firstItem;
    private LinkedList.Node lastItem;
    private int length;
    private int occupiedBoxes;

    /**
     * Constructor by default. No length or items needed.
     */
    public Dictionary() {
        this.entries = new LinkedList.Node[11];
    }

    /**
     * Constructor with initial length of the hash table specified.
     *
     * @param length of the initial hash table
     */
    public Dictionary(int length) {
        this.entries = new LinkedList.Node[nextPrimeNumber(length)];
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

    public void put(Object key, Object value) {
        int index = hash(key);
        Node nodo = entries[index];
        while (nodo.nextNode() != null && nodo.key().equals(key)) {
            entries[index] = nodo.nextNode();

        }
    }
}
