package org.ulpgc.edp.model.dct;

import org.ulpgc.edp.exceptions.*;
import org.ulpgc.edp.model.tpl.*;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;

/**
 * Class which represents a dictionary data structure.
 * It stores pairs of key - value on an efficient way.
 * <b>Accessing an element is almost O(1)</b>.
 *
 * <ul>
 * <li>Javier Castilla - <b>Principal and leader developer</b> of the class.</li>
 * <li>David Miranda - Special contribution in <b>put method</b> development.</li>
 * <li>Esteban Trujillo - Special contribution in <b>basic pop method</b> development.</li>
 * <li>Elena Artiles - Special contribution in <b>basic containsKey and clear methods</b> development.</li>
 * </ul>
 *
 * @author Javier Castilla
 * @author David Miranda
 * @author Esteban Trujillo
 * @author Elena Artiles
 * @see Tuple
 * @version 15-03-2024
 */
public class Dictionary implements Iterable<Object> {
    private static final double OV_FACTOR = 0.66;
    private static final int PERTURB_SHIFT = 5;
    private Integer[] indexes;
    private Node[] items;
    private int size, lastIndex, mask;

    /**
     * Constructor by default. No length or items needed.
     *
     * @author Javier Castilla
     */
    public Dictionary() {
        initDictionary();
    }

    /**
     * Constructor given an iterable of pairs key - value as tuples.
     * to put into the new dictionary.
     *
     * @param items to put into the new dictionary
     * @author Javier Castilla
     */
    public Dictionary(Iterable<Tuple> items) throws UnsupportedOperationException {
        initDictionary();

        for (Tuple item : items) {
            if (item.length() != 2) {
                throw new UnsupportedOperationException(
                        "The size of the pair differs from the expected (2)"
                );
            }
            put(item.get(0), item.get(1));
        }
    }

    /**
     * Constructor used to create a copy of a dictionary.
     *
     * @param dictionary to take items from
     * @author Javier Castilla
     */
    public Dictionary(Dictionary dictionary) {
        initDictionary();

        for (Tuple item : dictionary.items()) {
            put(item.get(0), item.get(1));
        }
    }

    /**
     * Constructor given some arguments dynamically. The first argument
     * will be the key, the next one its value, and so on and so for.
     *
     * @author Javier Castilla
     */
    public Dictionary(Object... items) throws UnsupportedOperationException {
        initDictionary();

        if (items.length % 2 != 0) {
            throw new UnsupportedOperationException(
                    "The number or arguments given does not match the" +
                            " needed dimensions"
            );
        }

        for (int i = 0; i < items.length - 1; i += 2) {
            put(items[i], items[i + 1]);
        }
    }

    /**
     * Clears all the dictionary, like if a new dictionary has been created.
     * @author Javier Castilla
     * @author Elena Artiles
     */
    public void clear() {
        initDictionary();
    }

    private void initDictionary() {
        indexes = new Integer[8];
        items = new Node[8];
        lastIndex = -1;
        mask = indexes.length - 1;
        size = 0;
    }

    /**
     * Method that returns the size of the current dictionary object.
     *
     * @return the number of elements in the dictionary
     * @author Javier Castilla
     */
    public int size() {
        return size;
    }

    /**
     * Method package protected used by iterable classes of the dictionary.
     *
     * @return items reference
     * @author Javier Castilla
     */
    Node[] entries() {
        return items;
    }

    /**
     * Method package protected used by iterable classes of the dictionary.
     *
     * @return indexes reference
     * @author Javier Castilla
     */
    Integer[] indexes() {
        return indexes;
    }

    /**
     * Static method that creates a new dictionary given some keys as an iterable
     * object. Value will be null as default.
     *
     * @param keys an iterable containing desire keys to add
     * @return a new dictionary containing given keys and null as values
     * @throws UnsupportedOperationException
     * @author Javier Castilla
     */
    public static Dictionary fromKeys(Iterable<Object> keys) {
        Dictionary newDictionary = new Dictionary();

        for (Object key : keys) {
            newDictionary.put(key, null);
        }

        return newDictionary;
    }

    /**
     * Static method that creates a new dictionary given some keys and values
     * as iterable objects.
     *
     * @param keys an iterable containing desire keys to add
     * @param values and iterable containing desire values to add
     * @return a new dictionary containing given keys and values
     * @throws UnsupportedOperationException
     * @author Javier Castilla
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
     * Private method used to store the given pair key - value
     *
     * @param key to store or update
     * @param value associated to the key
     * @param indexes to search for an available slot or update
     * @param items to store or update the given pair key - value
     * @author Javier Castilla
     * @author David Miranda
     */
    private void addEntries(
            Object key, Object value, Integer[] indexes, Node[] items
    ) {
        int index = hash(key, indexes);

        Integer i = indexes[index];
        if (i != null) {
            items[i].value(value);
            return;
        }

        indexes[index] = ++lastIndex;
        items[lastIndex] = new Node(key, value, index);
        size++;

        if (size > indexes.length * OV_FACTOR) {
            resize();
        }
    }

    /**
     * Inserts a pair key - value into the dictionary.
     *
     * @param key to store or update
     * @param value associated to the key
     * @author Javier Castilla
     */
    public void put(Object key, Object value) {
        addEntries(key, value, this.indexes, this.items);
    }

    /**
     * Private inner method used to remove a pair key - value located in the
     * given index.
     *
     * @param index
     * @return the removed value
     * @author Javier Castilla
     */
    private Object remove(int index) {
        Node node = items[indexes[index]];
        node.index(-1);
        indexes[index] = -1;
        size--;

        return node.value();
    }

    /**
     * Removes the pair key - value associated to the given key.
     *
     * @param key to remove
     * @return the value of the removed pair key - value
     * @throws KeyErrorException
     * @author Javier Castilla
     * @author Esteban Trujillo
     */
    public Object pop(Object key) throws KeyErrorException {
        int index = hash(key, indexes);

        Integer i = indexes[index];
        if (i == null) {
            throw new KeyErrorException(
                    "The given key is not in the dictionary", key
            );
        }

        return remove(index);
    }

    /**
     * Removes the pair key - value with the given key. If the key is not
     * into the dictionary, defaultValue will be returned.
     *
     * @param key to remove
     * @return the value of the removed pair key - value
     * @author Javier Castilla
     */
    public Object pop(Object key, Object defaultValue) {
        int index = hash(key, indexes);

        Integer i = indexes[index];
        if (i == null) {
            return defaultValue;
        }

        return remove(index);
    }

    /**
     * Removes the last introduced pair key - value.
     *
     * @return the removed pair key - value
     * @throws EmptyDictionaryException
     * @author Javier Castilla
     */
    public Tuple popitem() throws EmptyDictionaryException {
        if (size == 0) {
            throw new EmptyDictionaryException("The dictionary is empty");
        }

        Node node = items[lastIndex];
        indexes[node.index()] = -1;
        size--;
        return new Tuple(node.key(), node.value());
    }

    /**
     * Searches and returns the value paired with the given key. <b>If it is needed
     * to avoid an exception, use {@link #get(Object)}  or
     * {@link #get(Object, Object)}.</b>
     *
     * @param key to search the value
     * @return the value in pair with the given key
     * @throws KeyErrorException
     * @author Javier Castilla
     */
    public Object getItem(Object key) throws KeyErrorException {
        int index = hash(key, indexes);

        Integer i = indexes[index];
        if (i != null) {
            return items[i].value();
        }

        throw new KeyErrorException(
                "The given key is not in the dictionary", key
        );
    }

    /**
     * Searches and returns the value paired with the given key.
     * <b>This method could lead to confusions if some of the inserted values
     * were done as null. If not nullity of values could not be defended,
     * use {@link #get(Object, Object)} instead.</b>
     *
     * @param key to search the value
     * @return the value in pair with the given key if contained else null
     * @author Javier Castilla
     */
    public Object get(Object key) {
        return get(key, null);
    }

    /**
     * Searches and returns the value in pair with the given key.
     *
     * @param key to search the value
     * @return the value in pair with the given key if contained else defaultValue
     * @author Javier Castilla
     */
    public Object get(Object key, Object defaultValue) {
        int index = hash(key, indexes);

        Integer i = indexes[index];
        if (i == null) {
            return defaultValue;
        }

        return items[i].value();
    }

    /**
     * Returns the value paired with the key if exists, else adds the
     * given pair key - value to the dictionary and returns that value. Value
     * will be default as null.
     *
     * @param key to search or add
     * @return the value found or added
     * @author Javier Castilla
     */
    public Object setDefault(Object key) {
        return setDefault(key, null);
    }

    /**
     * Returns the value paired with the key if exists, else adds the
     * given pair key - value to the dictionary and returns that value.
     *
     * @param key to search or add
     * @param value to add if key not exists
     * @return the value found or added
     * @author Javier Castilla
     */
    public Object setDefault(Object key, Object value) {
        Object result = get(key);

        if (result == null) {
            put(key, value);
            return value;
        }

        return result;
    }

    /**
     * Updates the current dictionary with all the elements that
     * the dictionary given as a parameter has.
     *
     * @param dictionary to take items from
     * @author Javier Castilla
     */
    public void update(Dictionary dictionary) {
        for (Tuple item : dictionary.items()) {
            put(item.get(0), item.get(1));
        }
    }

    /**
     * Creates a copy of the current dictionary. Equivalent to instance
     * a dictionary with new Dictionary(Dictionary dictionary) constructor.
     *
     * @return a copy of a dictionary
     * @author Javier Castilla
     */
    public Dictionary copy() {
        return new Dictionary(this);
    }

    /**
     * Checks if the given key is contained into the dictionary.
     *
     * @param key
     * @return true if the key exists else false
     * @author Javier Castilla
     * @author Elena Artiles
     */
    public boolean containsKey(Object key) {
        Integer index = indexes[hash(key, indexes)];
        return index != null && index != -1;
    }

    /**
     * Private method used to find an empty slot or one that contains the
     * given key.
     *
     * @param key to compare
     * @param hash used to calculate the index
     * @param indexes to search in
     * @return an index where an available slot or existing key could be found
     * @author Javier Castilla
     */
    private int findSlot(Object key, int hash, Integer[] indexes) {
        int i = hash & mask;
        int perturb = hash;
        Integer index = indexes[i];

        while (true) {
            if (
                    index == null || (index != -1  && items[index].key().equals(key))
            ) break;
            perturb >>>= PERTURB_SHIFT;
            i = (i*PERTURB_SHIFT + perturb + 1) & mask;
            index = indexes[i];
        }

        return i;
    }

    /**
     * Private method used to get and available or wanted slot.
     *
     * @param key to apply the hash function
     * @param indexes search for and available or wanted slot
     * @return an index where and available or wanted slot is found
     * @author Javier Castilla
     */
    private int hash(Object key, Integer[] indexes) {
        return findSlot(key, key.hashCode(), indexes);
    }

    /**
     * Private method used to resize the dictionary when an overload factor
     * (OV_FACTOR) is reached.
     * @author Javier Castilla
     */
    private void resize() {
        int newLength = nextPowerOfTwo(items.length);
        int len = lastIndex;

        mask = newLength - 1;
        lastIndex = -1;
        size = 0;

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
     * Given a number, it returns the next power of two.
     *
     * @param num
     * @return the next power of two
     * @author Javier Castilla
     */
    private int nextPowerOfTwo(int num) {
        return num << 1;
    }


    /**
     * Returns an iterable containing all the keys in the dictionary.
     * Order of insertion is preserved.
     *
     * @return all the dictionary's keys iterable
     */
    public Iterable<Object> keys() {
        return new DictionaryKeysIterable(this);
    }

    /**
     * Returns an iterable containing all the values in the dictionary. Order of
     * insertion is preserved.
     *
     * @return all the dictionary's values iterable
     * @author Javier Castilla
     */
    public Iterable<Object> values() {
        return new DictionaryValuesIterable(this);
    }

    /**
     * Returns an iterable of arrays containing all the pairs key - value in the
     * dictionary. Order of insertion is preserved.
     *
     * @return all the dictionary's pairs key - value (Tuple) iterable
     * @author Javier Castilla
     */
    public Iterable<Tuple> items() {
        return new DictionaryItemsIterable(this);
    }

    /**
     * Iterator of the dictionary object.
     *
     * @return a dictionary's keys iterator
     * @author Javier Castilla
     */
    @Override
    public Iterator<Object> iterator() {
        return new DictionaryKeysIterable(this).iterator();
    }

    /**
     * Override method to know if other object is equals to the current object.
     *
     * @param object to compare
     * @return true if equals else false
     * @author Javier Castilla
     */
    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Dictionary other = (Dictionary) object;
        if (size() != other.size()) return false;

        for (Tuple item : other.items()) {
            if (!containsKey(item.get(0))) return false;
        }

        return true;
    }

    /**
     * Calculates and returns the hashCode value of the dictionary.
     *
     * @return hashCode value
     * @author Javier Castilla
     */
    @Override
    public int hashCode() {
        int result = Objects.hash(size);
        result = 31 * result + Arrays.hashCode(indexes);
        result = 31 * result + Arrays.hashCode(items);
        return result;
    }

    /**
     * String representation of the dictionary object.
     *
     * @return string representation
     * @author Javier Castilla
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
}
