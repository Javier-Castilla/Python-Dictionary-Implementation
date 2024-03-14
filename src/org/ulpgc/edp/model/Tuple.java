package org.ulpgc.edp.model;

import java.util.Arrays;
import java.util.Iterator;

/**
 * Tuple class used to storage elements, not allowing editing the data structure
 * once it is created.
 *
 * @author Javier Castilla
 * @author David Miranda
 * @author Esteban Trujillo
 * @author Elena Artiles
 */
public class Tuple implements Iterable<Object> {
    private Object[] items;
    private int length;

    /**
     * Constructor given dynamically all the items wanted to be into the Tuple.
     *
     * @param items to add into de Tuple
     */
    public Tuple(Object... items) {
        this.items = new Object[8];
        int index = 0;
        for (Object item : items) {
            if (index == this.items.length) {
                this.items = Arrays.copyOf(
                        this.items, this.items.length << 1
                );
            }
            this.items[index] = item;
            length++;
            index++;
        }
    }

    /**
     * Constructor given an iterable object. Used to iterate it and add the
     * elements into the Tuple.
     *
     * @param items to iterate and add into the Tuple
     */
    public Tuple(Iterable<Object> items) {
        this.items = new Object[8];
        int index = 0;
        for (Object item : items) {
            if (index == this.items.length) {
                this.items = Arrays.copyOf(
                        this.items, this.items.length << 1
                );
            }
            this.items[index] = item;
            index++;
        }
    }

    /**
     * Tuple items getter.
     *
     * @return tuple items reference
     */
    Object[] items() {
        return items;
    }

    /**
     * Method that return the length of the current Tuple object.
     *
     * @return current Tuple length
     */
    public int length() {
        return items.length;
    }

    /**
     * Method that returns the element contained in the specified index.
     *
     * @param index of the item
     * @return item at given index
     */
    public Object get(int index) {
        return items[index];
    }

    public int length() {
        return length;
    }

    /**
     * Overrided method that compares a given object with the current one.
     *
     * @param object to compare
     * @return true if objects are equals else false
     */
    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Tuple other = (Tuple) object;

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
     * Overrided method that returns the hashCode of the instanc3ed object.
     *
     * @return hashCode of the instance object
     */
    @Override
    public int hashCode() {
        return Arrays.hashCode(items);
    }

    /**
     * Overrided method used to iterate over the Tuple.
     *
     * @return an iterator of the Tuple class
     */
    @Override
    public Iterator<Object> iterator() {
        return new TupleItemsIterable(this).iterator();
    }

    /**
     * Overrided method that gives the string representation of the object.
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
                str.append(String.format("\'%s\'", item));
            } else {
                str.append(item);
            }
            str.append(", ");
        }

        if (items.length > 0) {
            str.setLength(str.length() - 2);
        }

        str.append(")");

        return str.toString();
    }
}
