package org.ulpgc.edp.model;

import java.util.Random;

class UniversalHash {
    private int a, b, length, prime;
    private static final Random generator = new Random();
    UniversalHash(int length, int prime) {
        this.length = length;
        this.prime = prime;
    }

    int calcHash(int x) {
        return ((a * x + b) % prime) % length;
    }

    void updateValues() {
        a = generator.nextInt(0, prime);
        b = generator.nextInt(0, prime);
    }
}
