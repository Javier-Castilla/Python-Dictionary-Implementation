package org.ulpgc.edp.model.tpl;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;

/**
 * Tuple class used to storage elements, not allowing editing the data structure
 * once it is created.
 *
 * @author Javier Castilla
 * @version 15-03-2024
 */
public class Tuple implements Iterable<Object> {
    private Object[] items;
    private int length;

    /**
     * Constructor given dynamically all the items wanted to be into the tuple.
     *
     * @param items to add into de tuple
     */
    public Tuple(Object... items) {
        this(Arrays.asList(items));
    }

    /**
     * Constructor given an iterable to build the new tuple. The elements
     * contained into that iterable must be tuples.
     *
     * @param items
     */
    public Tuple(Iterable<?> items) {
        this.items = new Object[8];
        int index = 0;

        for (Object item : items) {
            if (index == this.items.length) {
                this.items = Arrays.copyOf(
                        this.items, this.items.length << 1
                );
            }
            this.items[index++] = item;
            length++;
        }
    }

    /**
     * Tuple items package level getter.
     *
     * @return tuple items reference
     */
    Object[] items() {
        return items;
    }

    /**
     * Returns the element located in the specified index.
     *
     * @param index of the item
     * @return item at given index
     */
    public Object get(int index) {
        return items[index];
    }

    /**
     * Returns the tuple length.
     *
     * @return tuple length
     */
    public int length() {
        return length;
    }

    /**
     * Compares a given object with the current one, checking equality between them.
     *
     * @param object to compare
     * @return true if objects are equals else false
     */
    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Tuple other = (Tuple) object;
        if (length() != other.length()) return false;

        Iterator<Object> it1 = iterator();
        Iterator<Object> it2 = other.iterator();

        int counter = 0;
        while (it1.hasNext() && it2.hasNext()) {
            Object item1 = it1.next();
            Object item2 = it2.next();

            if (!item1.equals(item2)) return false;

            counter += 2;
        }

        return counter == this.length() * 2;
    }

    /**
     * Override method that returns the hashCode of the instanced object.
     *
     * @return hashCode of the instance object
     */
    @Override
    public int hashCode() {
        return Arrays.hashCode(items);
    }

    /**
     * Override method used to iterate over the Tuple.
     *
     * @return an iterator of the Tuple class
     */
    @Override
    public Iterator<Object> iterator() {
        return new TupleItemsIterable(this).iterator();
    }

    /**
     * Override method that gives the string representation of the object.
     *
     * @return the string representation
     */
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("(");

        for (Object item : items) {
            if (item == null) continue;
            if (item.getClass() == String.class) {
                str.append(String.format("'%s', ", item));
            } else {
                str.append(item + ", ");
            }
        }

        if (items.length > 0) {
            str.setLength(str.length() - 2);
        }

        str.append(")");

        return str.toString();
    }
}
