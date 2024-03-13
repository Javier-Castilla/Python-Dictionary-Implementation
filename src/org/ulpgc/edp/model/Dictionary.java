package org.ulpgc.edp.model;

import org.ulpgc.edp.exceptions.*;

import java.security.KeyException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;

/**
 * Class which represents a dictionary data structure.
 * It stores pairs of keys - values on an efficient way.
 * Accessing an element is almost O(1).
 *
 * @author Javier Castilla
 * @author David Miranda
 * @author Esteban Trujillo
 * @author Elena Artiles
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
        this.mask = indexes.length - 1;
    }

    /**
     * Constructor given an iterable of pairs key - value
     * to put into the new dictionary.
     *
     * @param items to put into the new dictionary
     */
    public Dictionary(Iterable<Object[]> items) {
        this.indexes = new Integer[8];
        this.items = new Node[8];
        this.mask = indexes.length - 1;
        Iterator<Object[]> itemsIterator = items.iterator();
        while (itemsIterator.hasNext()) {
            Object[] item = itemsIterator.next();
            put(item[0], item[1]);
        }
    }

    /**
     * Constructor used to create a copy of a Dictionary.
     *
     * @param dictionary to take items from
     */
    public Dictionary(Dictionary dictionary) {
        this.indexes = new Integer[8];
        this.items = new Node[8];
        this.mask = indexes.length - 1;
        for (Tuple item : dictionary.items()) {
            put(item.get(0), item.get(1));
        }
    }

    /**
     * Static method that creates a new Dictionary given some keys as iterable
     * object.
     *
     * @param keys an iterable containing desire keys to add
     * @return a new dictionary containing given keys and values
     * @throws UnsupportedOperationException
     */
    public static Dictionary fromKeys(Iterable<Object> keys) {
        Dictionary newDictionary = new Dictionary();
        Iterator<Object> keySet = keys.iterator();

        while (keySet.hasNext()) {
            newDictionary.put(keySet.next(), null);
        }

        return newDictionary;
    }

    /**
     * Static method that creates a new Dictionary given some keys and values
     * as iterable objects.
     *
     * @param keys an iterable containing desire keys to add
     * @param values and iterable containing desire values to add
     * @return a new dictionary containing given keys and values
     * @throws UnsupportedOperationException
     */
    public static Dictionary fromKeys(
            Iterable<Object> keys, Iterable<Object> values
    ) throws UnsupportedOperationException {
        Dictionary newDictionary = new Dictionary();
        Iterator<Object> keySet = keys.iterator();
        Iterator<Object> valueSet = values.iterator();

        int keysCounter = 0;
        int valuesCounter = 0;

        while (keySet.hasNext() && valueSet.hasNext()) {
            newDictionary.put(keySet.next(), valueSet.next());
            keysCounter++;
            valuesCounter++;
        }

        if (keysCounter != valuesCounter) {
            throw new UnsupportedOperationException("The sizes of keys and " +
                    "values differs");
        }

        return newDictionary;
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
        int perturb = hash;
        Integer index = indexes[i];
        while (index != null) {
            //if (index != -1  && items[index].key().equals(key)) break;
            perturb >>>= PERTURB_SHIFT;
            i = (i*PERTURB_SHIFT + perturb + 1) & mask;
            index = indexes[i];
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
     * Private method used to find a slot which contains the given key.
     *
     * @param key to compare
     * @param hash used to calculate the index
     * @return an index where the key is located or -1 if there is no such slot
     */
    private int findSlot(Object key, int hash) {
        int i = hash & mask;
        int perturb = hash;
        Integer index = indexes[i];
        while (true) {
            if (index != -1  && items[index].key().equals(key)) break;
            perturb >>>= PERTURB_SHIFT;
            i = (i*PERTURB_SHIFT + perturb + 1) & mask;
            index = indexes[i];
        }
        return (index != null) ? i : -1;
    }

    /**
     * Private method used to find a slot where the given key is located.
     *
     * @param key to compare
     * @return an index where and slot with the given key is located or -1 if
     * there is no such slot
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
    private void addEntries(
            Object key, Object value, Integer[] indexes, Node[] items
    ) {
        //if (!containsKey(key, indexes));

        int index = hash(key, indexes);

        Integer i = indexes[index];

        if (i != null) {
            items[i].value(value);
            return;
        }

        indexes[index] = ++lastIndex;
        items[lastIndex] = new Node(key, value, index);
        occupiedBoxes++;
        size++;

        if (occupiedBoxes > indexes.length * OV_FACTOR) {
            resize();
        }
    }

    /**
     * Size of the Dictionary object.
     *
     * @return the number of elements in the dictionary
     */
    public int size() {
        return size;
    }

    Node[] entries() {
        return items;
    }

    Integer[] indexes() {
        return indexes;
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
     * Returns the value associated with the key if exists, else adds the
     * given pair key - value to the Dictionary and returns that value. Value
     * will be default as null.
     *
     * @param key to search or add
     * @return the value found or added
     */
    public Object setDefault(Object key) {
        Object result;
        result = get(key);
        if (result == null) {
            put(key, null);
            return null;
        }
        return result;
    }

    /**
     * Returns the value associated with the key if exists, else adds the
     * given pair key - value to the Dictionary and return that value.
     *
     * @param key to search or add
     * @param value to add if key not exists
     * @return the value found or added
     */
    public Object setDefault(Object key, Object value) {
        Object result;
        result = get(key);
        if (result == null) {
            put(key, value);
            return value;
        }
        return result;
    }

    /**
     * Method that updates the current dictionary with all the elements that
     * the Dictionary given as a parameter has.
     *
     * @param dictionary
     */
    public void update(Dictionary dictionary) {
        for (Tuple item : dictionary.items()) {
            put(item.get(0), item.get(1));
        }
    }

    /**
     * Removes the pair key - value with the given key.
     *
     * @param key to remove
     * @return the value of the removed pair key - value
     * @throws KeyException
     */
    public Object pop(Object key) throws KeyException {
        int index = hash(key);

        if (index < 0) {
            throw new KeyException(
                    "The given key is not in the dictionary: " + key
            );
        }

        Node node = items[indexes[index]];
        node.index(-1);
        indexes[index] = -1;
        size--;

        return node.value();
    }

    /**
     * Removes the last introduced pair key - value.
     *
     * @return the removed pair key - value
     * @throws EmptyDictionaryException
     */
    public Tuple popitem() throws EmptyDictionaryException {
        if (size == 0) {
            throw new EmptyDictionaryException("The dictionary is empty");
        }

        Node node = items[lastIndex];

        if (node != null) {
            indexes[node.index()] = -1;
            size--;
            return new Tuple(node.key(), node.value());
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
     * @throws KeyException
     */
    public Object getItem(Object key) throws KeyException {
        int index = hash(key);

        if (index < 0) {
            throw new KeyException(
                    "The key is not contained into the dictionary"
            );
        }

        return items[indexes[index]].value();
    }

    /**
     * Searches and returns the value in pair with the given key.
     *
     * @param key to search the value
     * @return the value in pair with the given key
     * @throws KeyException
     */
    public Object get(Object key) {
        int index = hash(key);

        if (index < 0) {
            return null;
        }

        return items[indexes[index]].value();
    }

    /**
     * Searches and returns the value in pair with the given key.
     *
     * @param key to search the value
     * @return the value in pair with the given key
     * @throws KeyException
     */
    public Object get(Object key, Object value) {
        int index = hash(key);

        if (index < 0) {
            return value;
        }

        return items[indexes[index]].value();
    }

    /**
     * Method to test if a key is contained into the Dictionary.
     *
     * @param key to test
     * @return true if Dictionary contains key else false
     */
    public boolean containsKey(Object key, Integer[] indexes) {
        int index = hash(key, indexes);

        return index >= 0;
    }

    public boolean containsKey(Object key) {
        int index = hash(key);

        return index >= 0;
    }

    /**
     * Method that creates a copy of the current Dictionary. Equivalent to instance
     * a Dictionary with new Dictionary(Dictionary dictionary) constructor.
     *
     * @return a copy of a Dictionary
     */
    public Dictionary copy() {
        return new Dictionary(this);
    }

    /**
     * Returns an iterable containing all the keys in the Dictionary.
     * Order of insertion is preserved.
     *
     * @return all the dictionary's keys iterable
     */
    public Iterable<Object> keys() {
        return new DictionaryKeysIterable(this);
    }

    /**
     * Returns an iterable containing all the values in the Dictionary. Order of
     * insertion is preserved.
     *
     * @return all the dictionary's values iterable
     */
    public Iterable<Object> values() {
        return new DictionaryValuesIterable(this);
    }

    /**
     * Returns an iterable of arrays containing all the pairs key - value in the
     * Dictionary. Order of insertion is preserved.
     *
     * @return all the dictionary's pairs key - value iterable
     */
    public Iterable<Tuple> items() {
        return new DictionaryItemsIterable(this);
    }

    /**
     * Overrided method to know if other object is equals to current object.
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

        Iterator<Tuple> it1 = items().iterator();

        while (it1.hasNext()) {
            Tuple x = it1.next();
            if (!other.containsKey(x.get(0))) return false;
        }

        return true;
    }

    /**
     * Calculates and returns the hashCode value.
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
     * @return a Dictionary keys iterator
     */
    @Override
    public Iterator<Object> iterator() {
        return new DictionaryKeysIterable(this).iterator();
    }
}
