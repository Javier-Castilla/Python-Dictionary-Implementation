package org.ulpgc.edp.model;

import org.ulpgc.edp.exceptions.*;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;

/**
 * Class which represents a dictionary data structure.
 * It stores pairs of keys - values on an efficient way.
 * Accessing an element is almost O(1).
 *
 * @author Javier
 */
public class Dictionary implements Iterable<Object> {
    private Integer[] indexes;
    private Node[] items;
    private int lastIndex = -1;
    private int size;
    private int occupiedBoxes;
    private int mask;
    private static final double OV_FACTOR = 0.6;
    private static final int PERTURB_SHIFT = 3;

    /**
     * Constructor by default. No length or items needed.
     */
    public Dictionary() {
        this.indexes = new Integer[8];
        this.items = new Node[8];
        this.mask = indexes.length - 1;
    }

    /**
     * Constructor with initial length of the hash table specified.
     *
     * @param length of the initial hash table
     */
    public Dictionary(int length) {
        int itemsLen = ((int) (Math.log(length) / Math.log(2)) + 1);
        this.indexes = new Integer[1 << itemsLen];
        this.items = new Node[1 << itemsLen];
    }

    /**
     * Constructor given a data structure of pairs or tuples key - value
     * to put into the new dictionary.
     *
     * @param items to put into the new dictionary
     */
    public Dictionary(Object[][] items) {
        int itemsLen = (int) (Math.log(items.length) / Math.log(2)) + 1;
        this.indexes = new Integer[1 << itemsLen];
        this.items = new Node[1 << itemsLen];

        for (int index = 0; index < items.length; index++) {
            Object key = items[index][0];
            Object value = items[index][1];
            put(key, value);
        }
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

    /**
     * Private method used to know if a taken slot is available or not.
     *
     * @param key to compare
     * @param index to search into indexes array
     * @param indexes to take the pair key - value index
     * @return true if not available else false
     */
    private boolean isNotAvailableSlot(Object key, int index, Integer[] indexes) {
        if (indexes[index] != null && items[indexes[index]].key().equals(key) ||
                indexes[index] == null ||
                indexes[index] == -1
        ) {
            return false;
        }
        return true;
    }

    /**
     * Private method used to find an empty slot or one that contains the
     * given key.
     *
     * @param key to compare
     * @param hash used to calculate the index
     * @param indexes to search in
     * @return an index where an available slot or existing key could be found
     */
    private int findSlot(Object key, int hash, Integer[] indexes) {
        int i = hash & mask;

        for (int perturb = hash; isNotAvailableSlot(key, i, indexes);) {
            perturb >>= PERTURB_SHIFT;
            i = (i*PERTURB_SHIFT + perturb + 1) & mask;
        }
        return i;
    }

    /**
     * Private method used to get and available slot.
     *
     * @param key to apply the hash
     * @param indexes search for and available slot
     * @return an index where and available slot is found
     */
    private int hash(Object key, Integer[] indexes) {
        return findSlot(key, key.hashCode(), indexes);
    }

    /**
     * Private method used to know if a taken slot has the searched
     * key or not.
     *
     * @param key to compare
     * @param index to search into indexes array
     * @return true if not searched key else false
     */
    private boolean isNotSearchedKey(Object key, int index) {
        return indexes[index] != null && !items[indexes[index]].key().equals(key);
    }

    /**
     * Private method used to find a slot which contains the given key.
     *
     * @param key to compare
     * @param hash used to calculate the index
     * @return an index where the key is located or -1 if there is no such slot
     */
    private int findSlot(Object key, int hash) {
        int i = hash & mask;

        for (int perturb = hash; isNotSearchedKey(key, i);) {
            perturb >>= PERTURB_SHIFT;
            i = (i*PERTURB_SHIFT + perturb + 1) & mask;
        }
        return (indexes[i] != null) ? i : -1;
    }

    /**
     * Private method used to find a slot where the given key is located.
     *
     * @param key to compare
     * @return an index where and slot with the given key is located or -1 if
     * the is no such slot
     */
    private int hash(Object key) {
        return findSlot(key, key.hashCode());
    }

    /**
     * Private method used to resize the dictionary when an overload factor
     * (OV_FACTOR) is reached.
     */
    private void resize() {
        int newLength = nextPowerOfTwo(items.length);
        int len = lastIndex;

        mask = newLength - 1;
        lastIndex = -1;
        size = 0;
        occupiedBoxes = 0;

        Integer[] newIndexes = new Integer[newLength];
        Node[] newItems = new Node[newLength];

        for (int i = 0; i <= len; i++) {
            Node node = items[i];
            addEntries(node.key(), node.value(), newIndexes, newItems);
        }

        this.indexes = newIndexes;
        this.items = newItems;
    }

    /**
     * Private method used to store the given pair key - value
     *
     * @param key to store or update
     * @param value associated to the key
     * @param indexes to search for an available slot or update
     * @param items to store or update the given pair key - value
     */
    private void addEntries(Object key, Object value, Integer[] indexes, Node[] items) {
        int index = hash(key, indexes);

        if (indexes[index] != null) {
            items[indexes[index]].value(value);
            return;
        }

        indexes[index] = lastIndex + 1;
        items[lastIndex + 1] = new Node(key, value, index);
        lastIndex++;
        occupiedBoxes++;
        size++;

        if (occupiedBoxes > indexes.length * OV_FACTOR) {
            resize();
        }
    }

    /**
     *
     * @return the number of elements in the dictionary
     */
    public int size() {
        return size;
    }

    /**
     * Method used to put a pair key - value into the dictionary.
     *
     * @param key to store or update
     * @param value associated to the key
     */
    public void put(Object key, Object value) {
        addEntries(key, value, this.indexes, this.items);
    }

    /**
     * Method that updates the current dictionary with all the elements that
     * the dictionary given as a parameter has.
     *
     * @param dictionary
     */
    public void update(Dictionary dictionary) {
        for (Object[] item : dictionary.items()) {
            put(item[0], item[1]);
        }
    }

    /**
     * Removes the pair key - value with the given key.
     *
     * @param key to remove
     * @return the value of the removed pair key - value
     * @throws KeyErrorException if the given key is not in the dictionary
     */
    public Object pop(Object key) throws KeyErrorException {
        int index = hash(key);

        if (index < 0) {
            throw new KeyErrorException(
                    "The given key is not in the dictionary: " + key
            );
        }

        Node node = items[indexes[index]];
        indexes[index] = -1;
        size--;

        return node.value();
    }

    /**
     * Removes the last introduced pair key - value.
     *
     * @return the removed pair key - value
     * @throws EmptyDictionaryException if the dictionary is empty
     */
    public Object[] popitem() throws EmptyDictionaryException {
        if (size == 0) {
            throw new EmptyDictionaryException("The dictionary is empty");
        }

        Node node = items[lastIndex];

        if (node != null) {
            indexes[node.index()] = -1;
            size--;
            return new Object[]{node.key(), node.value()};
        }

        return null;
    }

    /**
     * Clears all the Dictionary, like if a new Dictionary has been created.
     */
    public void clear() {
        indexes = new Integer[indexes.length];
        items = new Node[items.length];
        lastIndex = -1;
        size = 0;
        occupiedBoxes = 0;
    }

    /**
     * Searches and returns the value in pair with the given key.
     *
     * @param key to search the value
     * @return the value in pair with the given key
     * @throws KeyErrorException if the given key is not in the dictionary
     */
    public Object get(Object key) throws KeyErrorException {
        int index = hash(key);

        if (index < 0) {
            throw new KeyErrorException(
                    "The key is not contained into the dictionary"
            );
        }

        return items[indexes[index]].value();
    }

    /**
     * Method to test is a key is contained into the Dictionary.
     *
     * @param key to test
     * @return true if Dictionary contains key else false
     */
    public boolean containsKey(Object key) {
        int index = hash(key);

        return index >= 0;
    }

    /**
     * Returns an iterable containing all the keys in the dictionary.
     * Order of insertion is preserved.
     *
     * @return all the dictionary's keys iterable
     */
    public Iterable<Object> keys() {
        return new DictionaryKeysIterator(indexes, items, this);
    }

    /**
     * Returns an iterable containing all the values in the dictionary. Order of
     * insertion is preserved.
     *
     * @return all the dictionary's values iterable
     */
    public Iterable<Object> values() {
        return new DictionaryValuesIterator(indexes, items, this);
    }

    /**
     * Returns an iterable of arrays containing all the pairs key - value in the
     * dictionary. Order of insertion is preserved.
     *
     * @return all the dictionary's pairs key - value iterable
     */
    public Iterable<Object[]> items() {
        return new DictionaryItemsIterator(indexes, items, this);
    }

    /**
     * Overrided method to know if other objects is equals to current object.
     *
     * @param object to compare
     * @return true if equals else false
     */
    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Dictionary other = (Dictionary) object;
        if (size != other.size()) return false;

        Iterator<Object[]> it1 = items().iterator();
        Iterator<Object[]> it2 = other.items().iterator();


        while (it1.hasNext() && it2.hasNext()) {
            if (it1.next() != it2.next()) return false;
        }

        return true;
    }

    /**
     * Calculates an returns the hashCode value
     *
     * @return hashCode value
     */
    @Override
    public int hashCode() {
        int result = Objects.hash(size);
        result = 31 * result + Arrays.hashCode(indexes);
        result = 31 * result + Arrays.hashCode(items);
        return result;
    }

    /**
     * String representation of the Dictionary object.
     *
     * @return string representation
     */
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();

        str.append("{");

        for (Node node : items) {
            if (node == null || node.index() == -1) continue;
            str.append(node);
            str.append(", ");
        }

        if (size != 0) {
            str.setLength(str.length() - 2);
        }

        str.append("}");

        return str.toString();
    }

    /**
     * Iterator of the Dictionary object.
     *
     * @return an iterator
     */
    @Override
    public Iterator<Object> iterator() {
        return new DictionaryKeysIterator(
                indexes, items, this
        ).iterator();
    }
}
