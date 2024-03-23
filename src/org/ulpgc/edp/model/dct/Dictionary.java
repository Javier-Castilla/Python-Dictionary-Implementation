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
 * @version 22-03-2024
 */
public class Dictionary implements Iterable<Object> {
    private static final double OV_FACTOR = 0.66;
    private static final int PERTURB_SHIFT = 5;
    private static final int INITIAL_SIZE = 8;
    private Integer[] indexes;
    private Node[] items;
    private int size, lastIndex, mask;

    /**
     * Constructor by default. Initializes an empty dictionary.
     *
     * @author Javier Castilla
     */
    public Dictionary() {
        initDictionary();
    }

    /**
     * Constructor given an iterable of pairs key - value as tuples
     * to put into the new dictionary.
     *
     * @param items to put into the new dictionary
     * @throws ValueErrorException if the elements contained
     * into the iterable are not tuples or length of those elements is not 2
     * @author Javier Castilla
     */
    public Dictionary(Iterable<?> items) throws ValueErrorException {
        initDictionary();

        for (Object item : items) {
            if (item.getClass() != Tuple.class) {
                throw new ValueErrorException(
                        "Pairs into iterable must be tuples"
                );
            }

            Tuple pair = (Tuple) item;

            if (pair.length() != 2) {
                throw new ValueErrorException(
                        "The size of the pair ("
                        + pair.length() + ") differs from the expected (2)"
                );
            }
            put(pair.get(0), pair.get(1));
        }
    }

    /**
     * Constructor given some arguments dynamically. The first argument
     * will be the key, the next one its value, and so on and so for.
     * It is absolutely necessary an even number of arguments.
     * @throws ValueErrorException if the number of elements is not even
     * @author Javier Castilla
     */
    public Dictionary(Object... items) throws ValueErrorException {
        initDictionary();

        if (items.length % 2 != 0) {
            throw new ValueErrorException(
                    "The number or arguments given does not match the" +
                            " needed dimensions. It must be multiple of 2"
            );
        }

        for (int i = 0; i < items.length - 1;) {
            put(items[i++], items[i++]);
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
        update(dictionary);
    }

    /**
     * Clears all the dictionary, like if a new empty dictionary has been created.
     * @author Javier Castilla
     * @author Elena Artiles
     */
    public void clear() {
        initDictionary();
    }

    /**
     * Initializes or reinitialize the current dictionary.
     *
     * @author Javier Castilla
     */
    private void initDictionary() {
        indexes = new Integer[INITIAL_SIZE];
        items = new Node[INITIAL_SIZE];
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
     * object. Value will be default as null.
     *
     * @param keys an iterable containing desire keys to add
     * @return a new dictionary containing given keys and null as values
     * @author Javier Castilla
     */
    public static Dictionary fromKeys(Iterable<?> keys) {
        Dictionary newDictionary = new Dictionary();

        for (Object key : keys) {
            newDictionary.put(key, null);
        }

        return newDictionary;
    }

    /**
     * Static method that creates a new dictionary given some keys as
     * an iterable and the value that will be in pair with all the
     * given keys.
     *
     * @param keys an iterable containing desire keys to add
     * @param value to associate with the given keys
     * @return a new dictionary containing given keys and values
     * @author Javier Castilla
     */
    public static Dictionary fromKeys(Iterable<?> keys, Object value) {
        Dictionary newDictionary = new Dictionary();

        for (Object key : keys) {
            newDictionary.put(key, value);
        }

        return newDictionary;
    }

    /**
     * Static method that creates a new dictionary given some keys as an array.
     * Value will be default as null.
     *
     * @param keys array containing all the keys desire to add
     * @return a new dictionary containing all given keys and null as value
     */
    public static Dictionary fromKeys(Object[] keys) {
        return Dictionary.fromKeys(Arrays.asList(keys));
    }

    /**
     * Static method that creates a new dictionary given some keys as an array
     * and a value to associate with the given keys.
     *
     * @param keys array containing all the keys desire to add
     * @param value to associate with the given keys
     * @return a new dictionary containing all given keys with the given value
     */
    public static Dictionary fromKeys(Object[] keys, Object value) {
        return Dictionary.fromKeys(Arrays.asList(keys), value);
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

        Integer itemIndex = indexes[index];
        if (itemIndex != null) {
            items[itemIndex].value(value);
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
     * Removes the pair key - value associated to the given key.
     * <b>If an exception is needed to be avoided, use {@link #pop(Object, Object)}</b>.
     *
     * @param key to remove
     * @return the value of the removed pair key - value
     * @throws KeyErrorException if the dictionary does not contain the given key
     * @author Javier Castilla
     * @author Esteban Trujillo
     */
    public Object pop(Object key) throws KeyErrorException {
        Object result = pop(key, null);

        if (result == null) {
            throw new KeyErrorException(key.toString());
        }

        return result;
    }

    /**
     * Removes the pair key - value with the given key. If the key is not
     * into the dictionary, a default value will be returned.
     *
     * @param key to remove from the dictionary
     * @return the value of the removed pair key - value
     * @author Javier Castilla
     */
    public Object pop(Object key, Object defaultValue) {
        int index = hash(key, indexes);

        Integer itemIndex = indexes[hash(key, indexes)];
        if (itemIndex == null) {
            return defaultValue;
        }

        return remove(index);
    }

    /**
     * Private inner method used to remove a pair key - value located in the
     * given index.
     *
     * @param index where the pair is located
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
     * Removes the last introduced pair key - value.
     *
     * @return the removed pair key - value
     * @throws KeyErrorException if the dictionary is empty
     * @author Javier Castilla
     */
    public Tuple popItem() throws KeyErrorException {
        if (size == 0) {
            throw new KeyErrorException("The dictionary is empty");
        }

        Node node = items[lastIndex--];
        indexes[node.index()] = -1;
        node.index(-1);
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
     * @throws KeyErrorException if the
     * @author Javier Castilla
     */
    public Object getItem(Object key) throws KeyErrorException {
        int index = hash(key, indexes);

        Integer itemIndex = indexes[index];
        if (itemIndex != null) {
            return items[itemIndex].value();
        }

        throw new KeyErrorException(key.toString());
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
     * @return the value in pair with the given key if contained else a default value
     * @author Javier Castilla
     */
    public Object get(Object key, Object defaultValue) {
        int index = hash(key, indexes);

        Integer itemIndex = indexes[index];
        if (itemIndex == null) {
            return defaultValue;
        }

        return items[itemIndex].value();
    }

    /**
     * Returns the value paired with the key if exists, else adds the
     * given pair key - value to the dictionary and returns that value. Value
     * will be default as null. <b>If it is needed a certain default value,
     * use {@link #setDefault(Object, Object)}</b>
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
     * a dictionary with new {@link #Dictionary(Dictionary dictionary)} constructor.
     *
     * @return a copy of the dictionary
     * @author Javier Castilla
     */
    public Dictionary copy() {
        return new Dictionary(this);
    }

    /**
     * Checks if the given key is contained into the dictionary.
     *
     * @param key to search
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
     * @param indexes array to search in
     * @return an index where an available slot or existing key could be found
     * @author Javier Castilla
     */
    private int hash(Object key, Integer[] indexes) {
        int index = key.hashCode() & mask;
        int perturb = key.hashCode();
        Integer itemIndex = indexes[index];

        while (itemIndex != null) {
            if (
                    (itemIndex != -1  && items[itemIndex].key().equals(key))
            ) break;
            perturb >>>= PERTURB_SHIFT;
            index = (index * PERTURB_SHIFT + perturb + 1) & mask;
            itemIndex = indexes[index];
        }

        return index;
    }

    /**
     * Private method used to resize the dictionary when an overload factor
     * (OV_FACTOR) is reached. The overload factor is originally defined as 2/3.
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
     * @param num to shift
     * @return the next power of two
     * @author Javier Castilla
     */
    private int nextPowerOfTwo(int num) {
        if (num > 0 && (num & (num - 1)) == 0) {
            return num << 1;
        }

        int power = (int) Math.ceil(Math.log(num) / Math.log(2));
        return 1 << power;
    }

    /**
     * Returns an iterable containing all the keys in the dictionary.
     * Basically, it is a dynamic view of the keys, if the dictionary
     * associated to that view is changed, the view will be too.
     * Order of insertion is preserved.
     *
     * @return all the dictionary's keys iterable
     */
    public Iterable<Object> keys() {
        return new DictionaryKeysIterable(this);
    }

    /**
     * Returns an iterable containing all the values in the dictionary.
     * Basically, it is a dynamic view of the values, if the dictionary
     * associated to that view is changed, the view will be too.Order of
     * insertion is preserved.
     *
     * @return all the dictionary values iterable
     * @author Javier Castilla
     */
    public Iterable<Object> values() {
        return new DictionaryValuesIterable(this);
    }

    /**
     * Returns an iterable of arrays containing all the pairs key - value in the
     * dictionary. Basically, it is a dynamic view of the items, if the dictionary
     * associated to that view is changed, the view will be too.
     * Order of insertion is preserved.
     *
     * @return all the dictionary pairs key - value (Tuple) iterable
     * @author Javier Castilla
     */
    public Iterable<Tuple> items() {
        return new DictionaryItemsIterable(this);
    }

    /**
     * Iterator method of the dictionary object.
     *
     * @return a dictionary keys iterator
     * @author Javier Castilla
     */
    @Override
    public Iterator<Object> iterator() {
        return new DictionaryKeysIterable(this).iterator();
    }

    /**
     * Compares a given object with the current dictionary to check equality.
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
        if (size != other.size()) return false;

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
     * String representation of the dictionary.
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
            str.append(node + ", ");
        }

        if (size != 0) {
            str.setLength(str.length() - 2);
        }

        str.append("}");

        return str.toString();
    }
}
