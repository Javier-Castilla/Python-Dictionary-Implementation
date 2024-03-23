package org.ulpgc.edp.tests;

import org.junit.*;
import org.ulpgc.edp.model.dct.Dictionary;
import org.ulpgc.edp.model.tpl.Tuple;
import java.util.Iterator;
import static org.junit.Assert.*;

/**
 * Testing class for test all the dictionary constructors.
 *
 * @author Javier Castilla
 * @version 22-03-2024
 */
public class TestDictionaryConstructors {
    /**
     * Test about initializing empty dictionary.
     */
    @Test
    public void testDefaultConstructor() {
        Dictionary dictionary = new Dictionary();

        assertEquals(
                "Wrong string of empty dictionary",
                "{}", dictionary.toString()
        );
    }

    /**
     * Test about initializing dictionary with iterable.
     */
    @Test
    public void testIterableConstructor() {
        Dictionary dictionary = new Dictionary(
                () -> new Iterator<>() {
                    private int index = 0;

                    @Override
                    public boolean hasNext() {
                        return index < 10;
                    }

                    @Override
                    public Tuple next() {
                        return new Tuple(index, index++ * 10);
                    }
                }
        );

        Dictionary otherDictionary = new Dictionary();
        for (int i = 0; i < 10;) {
            otherDictionary.put(i, i++ * 10);
        }

        assertTrue(
                "Dictionaries must be equals",
                dictionary.equals(otherDictionary)
        );
    }

    /**
     * Test about initializing dictionary with dynamic number of arguments.
     */
    @Test
    public void testDynamicArgsConstructor() {
        Dictionary otherDictionary = new Dictionary();
        for (int i = 0; i < 5; i += 2) {
            otherDictionary.put(i, i + 1);
        }

        Dictionary dictionary = new Dictionary(0, 1, 2, 3, 4, 5);

        assertTrue(
                "Dictionaries must be equals",
                dictionary.equals(otherDictionary)
        );
    }

    /**
     * Test about initializing dictionary with other dictionary.
     */
    @Test
    public void testFromDictConstructor() {
        Dictionary otherDictionary = new Dictionary();
        for (int i = 0; i < 5;) {
            otherDictionary.put(i, i++);
        }

        Dictionary dictionary = new Dictionary(otherDictionary);

        assertTrue(
                "Dictionaries must be equals",
                dictionary.equals(otherDictionary)
        );
    }
}
