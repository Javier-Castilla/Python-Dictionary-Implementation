package org.ulpgc.edp.model.dct;

import org.ulpgc.edp.exceptions.*;
import org.ulpgc.edp.flags.*;
import org.ulpgc.edp.model.tpl.*;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;


/**
 * Class which represents a dictionary data structure.
 * It stores pairs of key - value on an efficient way.
 * <b>Accessing an element is almost O(1)</b>.
 *
 * <ul>
 * <li>Javier Castilla - <b>Principal and leader developer</b> of the class.</li>
 * <li>David Miranda - Special contribution in <b>put method</b> development.</li>
 * <li>Esteban Trujillo - Special contribution in <b>basic pop method</b> development.</li>
 * <li>Elena Artiles - Special contribution in <b>basic clear method</b> development.</li>
 * </ul>
 *
 * @author Javier Castilla
 * @author David Miranda
 * @author Esteban Trujillo
 * @author Elena Artiles
 * @see Tuple
 * @see DictionaryKeys
 * @see DictionaryValues
 * @see DictionaryItems
 * @version 28-03-2024
 */
public class Dictionary implements Iterable<Object> {
    private static final double OV_FACTOR = 0.66;
    private static final int PERTURB_SHIFT = 5;
    private static final int INITIAL_SIZE = 8;
    private static final int REMOVED = -1;
    private static final Flags NONE = Flags.NONE;
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
     * to put into the new dictionary. Length of tuples must be 2, nor more
     * neither less. The same result as initializing an empty dictionary and
     * calling {@link #update(Iterable)} later.
     *
     * @param items to put into the new dictionary
     * @throws TypeError if element of tuple is unhashable
     * @throws ValueError if a container with size different from 2 is found
     * @author Javier Castilla
     */
    public Dictionary(Iterable<?> items) {
        initDictionary();
        update(items);
    }

    /**
     * Constructor given some arguments dynamically. The number of arguments
     * must be even. This is necessary because the first element will be the
     * key and the second one its value. The same result as initializing an
     * empty dictionary and calling {@link #update(Object...)} later.
     *
     * @throws TypeError if some key element is unhashable
     * @throws ValueError if number of arguments is not even
     * @author Javier Castilla
     */
    public Dictionary(Object... items) {
        initDictionary();
        update(items);
    }

    /**
     * Constructor used to create a copy of a dictionary. The same result as
     * initializing an empty dictionary and calling {@link #update(Dictionary)} later.
     *
     * @param dictionary to take items from
     * @author Javier Castilla
     */
    public Dictionary(Dictionary dictionary) {
        initDictionary();
        update(dictionary);
    }

    /**
     * Clears all the dictionary, like if the dictionary had been initialized.
     *
     * @author Javier Castilla
     * @author Elena Artiles
     */
    public void clear() {
        initDictionary();
    }

    /**
     * Initialize or reinitialize the current dictionary.
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
     * Method package protected used by iterable classes of the dictionary.
     *
     * @return last used index
     * @author Javier Castilla
     */
    int getLastIndex() {
        return lastIndex;
    }

    /**
     * Creates a new dictionary given some keys as an iterable
     * object. Value will be default as null. Keys cannot be duplicated,
     * but if they are, it will appear only once.
     *
     * @param keys an iterable containing desire keys to add
     * @return a new dictionary containing given keys and null as values
     * @throws TypeError if some key is unhashable
     * @author Javier Castilla
     */
    public static Dictionary fromKeys(Iterable<?> keys) {
        return fromKeys(keys, null);
    }

    /**
     * Creates a new dictionary given a dynamic number of arguments.
     * Value will be default as null.
     *
     * @param keys array containing all the keys desire to add
     * @throws TypeError if some key is unhashable
     * @return a new dictionary containing all given keys and null as value
     */
    public static Dictionary fromKeys(Object... keys) {
        return fromKeys(createIterator(keys), null);
    }

    /**
     * Creates a new dictionary given some keys as
     * an iterable and the value that will be in pair with all the
     * given keys.
     *
     * @param keys an iterable containing desire keys to add
     * @param value to associate with the given keys
     * @return a new dictionary containing given keys and values
     * @throws TypeError if some key is unhashable
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
     * Calculate the union of two given dictionary items sets. The order of insertion
     * in the result dictionary will be given by the order that the parameters
     * are given. If keys sets are the same, dict2 take priority over dict1.
     *
     * @param dict1 to make union
     * @param dict2 to make union
     * @return a new dictionary result of the union of other two
     */
    public static Dictionary union(Dictionary dict1, Dictionary dict2) {
        Dictionary unionDictionary = new Dictionary(dict1);
        unionDictionary.update(dict2);
        return unionDictionary;
    }

    /**
     * Creates an iterator to be used in some needed method of this class.
     * The parameter given must be an array.
     *
     * @param item array used to create the iterator
     * @return iterator from given item
     */
    private static Iterator<?> createIterator(Object[] item) {
        return new Iterator<>() {
            private int index = 0;
            @Override
            public boolean hasNext() {
                return index < item.length;
            }

            @Override
            public Object next() {
                if (hasNext()) {
                    return item[index++];
                }
                throw new NoSuchElementException();
            }
        };
    }

    /**
     * Inserts a pair key - value into the dictionary. If the key is already
     * into the dictionary items set, its value will be replaced with the
     * given one. <b>If it is needed to avoid this updating, use {@link #setDefault(Object)}
     * or {@link #setDefault(Object, Object)} instead.</b> No mutable object
     * is allowed as key, otherwise, an exception will be thrown.
     *
     * Non accepted key objects are:
     * <u>
     *     {@link Collection}
     *     {@link Map}
     *     {@link java.util.Arrays}
     *     {@link Dictionary}
     * </u>
     *
     * @param key to store or update
     * @param value associated to the key
     * @throws TypeError if key is unhashable
     * @author Javier Castilla
     */
    public void put(Object key, Object value) {
        if (key != null && (key instanceof Collection<?>
                || key instanceof Map<?,?>
                || key.getClass().isArray()
                || key.getClass() == Dictionary.class)
        ) throw new TypeError("unhashable type", key);

        addEntries(key, value, this.indexes, this.items);
    }

    /**
     * Private method used to store the given pair key - value.
     *
     * @param key to store or update
     * @param value associated to the key
     * @param indexes to search for an available slot or update
     * @param items where the given pair will be stored r updated
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
     * Removes the pair key - value associated to the given key.
     * Non element is returned when deleted.
     * <b>If it is needed to know if the element was deleted,
     * use {@link #pop(Object)} or {@link #pop(Object, Object)} instead</b>.
     *
     * @param key to delete
     * @throws KeyError if the dictionary does not contain the given key
     * @author Javier Castilla
     */
    public void del(Object key) {
        pop(key);
    }

    /**
     * Removes and returns the pair key - value associated to the given key.
     * <b>If an exception is needed to be avoided, use {@link #pop(Object, Object)}</b>.
     *
     * @param key to remove
     * @return the value of the removed pair key - value
     * @throws KeyError if the dictionary does not contain the given key
     * @author Javier Castilla
     * @author Esteban Trujillo
     */
    public Object pop(Object key) {
        Object result = pop(key, NONE);

        if (result == NONE) {
            throw new KeyError(key);
        }

        return result;
    }

    /**
     * Removes the pair key - value with the given key. If the key is not
     * into the dictionary, a default value will be returned.
     *
     * @param key to remove from the dictionary
     * @param defaultValue to return if the key is not contained
     * @return the value of the removed pair key - value
     * @author Javier Castilla
     */
    public Object pop(Object key, Object defaultValue) {
        int index = hash(key, indexes);

        Integer itemIndex = indexes[index];
        if (itemIndex == null || itemIndex == REMOVED) {
            return defaultValue;
        }

        if (itemIndex == lastIndex) lastIndex--;
        Node node = items[itemIndex];
        node.index(REMOVED);
        indexes[index] = REMOVED;
        size--;

        return node.value();
    }

    /**
     * Removes the last introduced pair key - value.
     *
     * @return the removed pair key - value
     * @throws KeyError if the dictionary is empty
     * @author Javier Castilla
     */
    public Tuple popItem() {
        if (size == 0) {
            throw new KeyError("'popItem(): dictionary is empty'");
        }

        Node node = items[lastIndex--];
        indexes[node.index()] = REMOVED;
        node.index(REMOVED);
        size--;

        return new Tuple(node.key(), node.value());
    }

    /**
     * Searches and returns the value paired with the given key. <b>If it is needed
     * to avoid an exception, use {@link #get(Object)}  or
     * {@link #get(Object, Object)}.</b>
     *
     * @param key to search its value
     * @return the value in pair with the given key
     * @throws KeyError if the given key is not contained
     * @author Javier Castilla
     */
    public Object getItem(Object key) {
        Object result = get(key, NONE);

        if (result == NONE) {
            throw new KeyError(key);
        }

        return result;
    }

    /**
     * Searches and returns the value paired with the given key.
     * <b>This method could lead to ambiguity if some of the inserted values
     * were done as null. If not nullity of values could not be defended,
     * use {@link #get(Object, Object)} instead.</b>
     *
     * @param key to search its value
     * @return the value in pair with the given key if contained else null
     * @author Javier Castilla
     */
    public Object get(Object key) {
        return get(key, null);
    }

    /**
     * Searches and returns the value in pair with the given key.
     *
     * @param key to search its value
     * @return the value in pair with the given key if contained else a default value
     * @author Javier Castilla
     */
    public Object get(Object key, Object defaultValue) {
        int index = hash(key, indexes);

        Integer itemIndex = indexes[index];
        if (itemIndex == null || itemIndex == REMOVED) {
            return defaultValue;
        }

        return items[itemIndex].value();
    }

    /**
     * Returns the value paired with the key if exists, else adds the
     * given pair key - value to the dictionary and returns that value. Value
     * will be default as null. <b>If it is needed a certain default value,
     * use {@link #setDefault(Object, Object)} instead.</b>
     *
     * @param key to search or add
     * @return the value found or added
     * @author Javier Castilla
     */
    public Object setDefault(Object key) {
        return setDefault(key, null);
    }

    /**
     * Returns the value of the key if exists, else adds the
     * given pair key - value to the dictionary and returns that value.
     *
     * @param key to search or add
     * @param defaultValue to add if key not exists
     * @return the value of the found key or the value added
     * @author Javier Castilla
     */
    public Object setDefault(Object key, Object defaultValue) {
        Object result = get(key, NONE);

        if (result == NONE) {
            put(key, defaultValue);
            return defaultValue;
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
        update(dictionary.items());
    }

    /**
     * Updates the current dictionary with the elements given dynamically.
     * Note that the number of given arguments must be even. This is needed
     * because the first element will be the key associated to the second one,
     * and so on.
     *
     * @param items to insert into the dictionary
     * @throws ValueError if number of arguments is not even
     */
    public void update(Object... items) {
        if (items.length % 2 != 0) {
            throw new ValueError(
                    "dictionary update sequence element has length " +
                            items.length + "; 2 is required"
            );
        }

        for (int i = 0; i < items.length;) {
            put(items[i++], items[i++]);
        }
    }

    /**
     * Updates the current dictionary with all the elements given by the iterable.
     * Elements into iterable must be sets of length 2, nor mores neither less.
     * If the iterable returns an element that cannot be converted to a sequence
     * an exception will be thrown.
     *
     * @param items to iterate and take key - value from
     * @throws TypeError if some element cannot be converted to a sequence
     * @throws ValueError if some element has length different of 2
     */
    public void update(Iterable<?> items) {
        int index = 0;
        for (Object item : items) {
            int size;
            Iterator<?> iterator;
            if (item instanceof Iterable) {
                size = getSuitableLength((Iterable<?>) item);
                iterator = ((Iterable<?>) item).iterator();
            } else {
                throw new TypeError(
                        "cannot convert dictionary update sequence #" +
                                index + " element to a sequence", item
                );
            }

            if (size != 2) {
                throw new ValueError(
                        "dictionary update sequence #" + index
                                + " element has length " + size + "; 2 is required"
                );
            }

            put(iterator.next(), iterator.next());
            index++;
        }
    }

    /**
     * Calculates the length of the given iterable.
     *
     * @param iterable to calculate length
     * @return iterable length
     */
    private int getSuitableLength(Iterable<?> iterable) {
        int length = 0;
        for (Object ignored : iterable) length++;
        return length;
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
        return index != null && index != REMOVED;
    }

    /**
     * Creates a DictionaryKeys view and reverse its content.
     *
     * @return a DictionaryKeys view reversed
     * @see DictionaryKeys
     * @author Javier Castilla
     */
    public DictionaryKeys reverse() {
        return new DictionaryKeys(this).reverse();
    }

    /**
     * Private method used to find an empty slot or one that contains the
     * given key. Null key will have index 0 by default.
     *
     * @param key to compare
     * @param indexes array to search in
     * @return an index where an available slot or existing key could be found
     * @author Javier Castilla
     */
    private int hash(Object key, Integer[] indexes) {
        if (key == null) return 0;
        int perturb = key.hashCode();
        int index = perturb & mask;
        Integer itemIndex = indexes[index];

        while (itemIndex != null) {
            if (
                    (itemIndex != REMOVED  && items[itemIndex].key().equals(key))
            ) break;
            perturb >>>= PERTURB_SHIFT;
            index = (index * PERTURB_SHIFT + perturb + 1) & mask;
            itemIndex = indexes[index];
        }

        return index;
    }

    /**
     * Private method used to increments hash table of the dictionary when an
     * overload factor (OV_FACTOR) is reached and rehashes the existing pairs.
     * The overload factor is originally defined as 2/3.
     *
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
     * @return all the dictionary keys iterable
     * @see DictionaryKeys
     * @author Javier Castilla
     */
    public DictionaryKeys keys() {
        return new DictionaryKeys(this);
    }

    /**
     * Returns an iterable containing all the values in the dictionary.
     * Basically, it is a dynamic view of the values, if the dictionary
     * associated to that view is changed, the view will be too.Order of
     * insertion is preserved.
     *
     * @return all the dictionary values iterable
     * @see DictionaryValues
     * @author Javier Castilla
     */
    public DictionaryValues values() {
        return new DictionaryValues(this);
    }

    /**
     * Returns an iterable of arrays containing all the pairs key - value in the
     * dictionary. Basically, it is a dynamic view of the items, if the dictionary
     * associated to that view is changed, the view will be too.
     * Order of insertion is preserved.
     *
     * @return all the dictionary pairs key - value (Tuple) iterable
     * @see DictionaryItems
     * @author Javier Castilla
     */
    public DictionaryItems items() {
        return new DictionaryItems(this);
    }

    /**
     * Iterator method of the dictionary object.
     *
     * @return a dictionary keys iterator
     * @see DictionaryKeys
     * @author Javier Castilla
     */
    @Override
    public Iterator<Object> iterator() {
        return new DictionaryKeys(this).iterator();
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
        int result = 0;
        for (Tuple item : items()) {
            result += 5 * item.hashCode();
        }
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
            if (node == null || node.index() == REMOVED) continue;
            str.append(node).append(", ");
        }

        if (size != 0) {
            str.setLength(str.length() - 2);
        }

        str.append("}");

        return str.toString();
    }
}
