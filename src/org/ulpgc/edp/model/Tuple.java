package org.ulpgc.edp.model;

import java.util.Arrays;
import java.util.Iterator;

public class Tuple implements Iterable<Object> {
    private Object[] items;
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
            index++;
        }
    }

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

    Object[] items() {
        return items;
    }

    public Object get(int index) {
        return items[index];
    }

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

            counter++;
        }

        return counter == items().length * 2;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(items);
    }

    @Override
    public Iterator<Object> iterator() {
        return new TupleItemsIterable(this).iterator();
    }

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
