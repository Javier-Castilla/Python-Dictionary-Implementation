package org.ulpgc.edp.model;

import java.util.Objects;

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

    private int hash(Object key) {
        return Objects.hash(key) % length;
    }

    private class Node {
        private Object key;
        private Object value;
        private Node prevNode;
        private Node nextIntroducedNode;
        private Node prevIntroducedNode;
        private boolean isRoot;

        private Node(Object key, Object value, boolean isRoot) {
            this.key = key;
            this.value = value;
            this.isRoot = isRoot;
            Dictionary.this.lastItem = this;
        }

        @Override
        public boolean equals(Object object) {
            if (this == object) return true;
            if (object == null || getClass() != object.getClass()) return false;
            Node node = (Node) object;
            return Objects.equals(key, node.key) && Objects.equals(value, node.value);
        }
    }
}
