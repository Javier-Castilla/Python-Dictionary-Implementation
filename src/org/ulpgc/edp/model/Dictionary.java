package org.ulpgc.edp.model;

import org.ulpgc.edp.exceptions.*;
import java.util.Iterator;

/**
 * Class which represents a dictionary data structure.
 * It stores pairs of keys - values on an efficient way.
 * Accessing an element is almost O(1).
 */
public class Dictionary implements Iterable<Object> {
    private LinkedList[] entries;
    private LinkedList.Node firstIntroducedNode;
    private LinkedList.Node lastIntroducedNode;
    private int length;
    private int occupiedBoxes;

    /**
     * Constructor by default. No length or items needed.
     */
    public Dictionary() {
        this.entries = new LinkedList[11];
    }

    /**
     * Constructor with initial length of the hash table specified.
     *
     * @param length of the initial hash table
     */
    public Dictionary(int length) {
        this.entries = new LinkedList[nextPrimeNumber(length)];
    }

    /**
     * Constructor given a data structure of pairs or tuples key - value
     * to put into the new dictionary.
     *
     * @param items to put into the new dictionary
     */
    public Dictionary(Object[][] items) throws KeyErrorException {
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

    public LinkedList.Node lastIntroducedNode() {
        return lastIntroducedNode;
    }

    public LinkedList[] entries() {
        return entries;
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

    /**
     * Given a number, it returns the next power of two.
     *
     * @param num
     * @return the next power of two
     */
    private int nextPowerOfTwo(int num) {
        return num << 1;
    }

    private int hash(Object key) {
        return key.hashCode() % entries.length;
    }

    public void put(Object key, Object value) throws KeyErrorException {
        if (key.getClass().isArray()) {
            throw new KeyErrorException("No immutable types allowed as keys.");
        }

        int index = hash(key);
        LinkedList list = entries[index];

        if (list == null) {
            list = new LinkedList();
            entries[index] = list;
            occupiedBoxes++;
        }

        LinkedList.Node node = list.append(key, value, lastIntroducedNode);

        if (node != null) {
            length++;
        }
        
        if (firstIntroducedNode == null) firstIntroducedNode = node;
        lastIntroducedNode = node;
    }

    /**
     * Removes the pair key - value with the given key.
     *
     * @param key to remove
     * @return the value of the removed pair key - value
     */
    public Object pop(Object key) throws KeyErrorException {
        int index = hash(key);

        LinkedList list = entries[index];

        if (list == null) {
            throw new KeyErrorException("The given key is not in the dictionary");
        }

        LinkedList.Node node = list.pop(key.toString());

        if (node == null) {
            throw new KeyErrorException("The given key is not in the dictionary");
        }

        if (firstIntroducedNode.equals(node)) {
            firstIntroducedNode = node.nextIntroducedNode();
        }

        if (lastIntroducedNode.equals(node)) {
            lastIntroducedNode = node.prevIntroducedNode();
        }

        length--;
        return node.value();
    }

    /**
     * Removes the last introduced pair key - value.
     *
     * @return the removed pair key - value
     * @throws EmptyDictionaryException
     */
    public Object popitem() throws EmptyDictionaryException {
        if (length == 0) {
            throw new EmptyDictionaryException("The dictionary is empty.");
        }

        int index = hash(lastIntroducedNode.key());

        //System.out.println(lastIntroducedNode.key());

        LinkedList list = entries[index];

        LinkedList.Node node = list.pop(lastIntroducedNode.key());

        LinkedList.Node prev = node.prevIntroducedNode();

        if (prev != null) {
            prev.nextIntroducedNode(null);
        }

        lastIntroducedNode = prev;

        length--;
        return node.value();
    }

    /**
     * Searches and returns the value in pair with the given key.
     *
     * @param key to search the value
     * @return the value in pair with the given key
     */
    public Object get(Object key) throws KeyErrorException {
        int index = hash(key);
        LinkedList list = entries[index];

        if (list == null) {
            throw new KeyErrorException("The given key is not in the dictionary");
        }

        LinkedList.Node node = list.get(key);

        if (node == null) {
            throw new KeyErrorException("The given key is not in the dictionary");
        }

        return node.value();
    }

    public boolean containsKey(Object key) {
        int index = hash(key);

        LinkedList list = entries[index];

        if (list == null) {
            return false;
        }

        LinkedList.Node node = list.get(key);

        if (node == null) {
            return false;
        }

        return true;
    }

    /**
     * Returns an Array containing all the keys in the dictionary.
     * Order of insertion is preserved.
     *
     * @return all the dictionary's keys
     */
    public Iterable<Object> keys() {
        return new DictionaryKeysIterator(this, firstIntroducedNode);
    }

    /**
     * Returns an Array containing all the values in the dictionary. Order of
     * insertion is preserved.
     *
     * @return all the dictionary's values
     */
    public Iterable<Object> values() {
        return new DictionaryValuesIterator(this, firstIntroducedNode);
    }

    /**
     * Returns an Array of arrays containing all the pairs key - value in the
     * dictionary. Order of insertion is preserved.
     *
     * @return all the dictionary's pairs key - value
     */
    public Iterable<Object[]> items() {
        return new DictionaryItemsIterator(this, firstIntroducedNode);
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();

        str.append("{");

        LinkedList.Node current = firstIntroducedNode;

        while (current != null) {
            str.append(current);
            if (current.nextIntroducedNode() != null) {
                str.append(", ");
            }
            current = current.nextIntroducedNode();
        }

        str.append("}");

        return str.toString();
    }

    @Override
    public Iterator<Object> iterator() {
        return new DictionaryKeysIterator(this, firstIntroducedNode).iterator();
    }
}
