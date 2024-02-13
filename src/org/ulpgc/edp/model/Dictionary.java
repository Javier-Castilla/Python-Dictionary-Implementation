package org.ulpgc.edp.model;

public class Dictionary {
    private Node[] entries;
    private Node lastItem;
    private int length;

    public Dictionary() {
        this.entries = new Node[11];
    }

    public Dictionary(int length) {
        this.entries = new Node[nextPrimeNumber(length)];
    }

    public Dictionary(Object[][] items) {
        for (int index = 0; index < items.length; index++) {
            Object key = items[index][0];
            Object value = items[index][1];
            put(key, value);
        }
    }

    public int length() {
        return length;
    }

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
}
