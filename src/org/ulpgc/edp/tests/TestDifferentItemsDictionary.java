package org.ulpgc.edp.tests;

import org.junit.*;
import org.ulpgc.edp.model.dct.Dictionary;
import org.ulpgc.edp.model.tpl.Tuple;
import java.util.Iterator;
import static org.junit.Assert.*;

/**
 * Testing class for a dictionary containing several value types in it.
 *
 * @author Javier Castilla
 * @version 28-03-2024
 */
public class TestDifferentItemsDictionary {
    private Dictionary dictionary;

    /**
     * Initial state of every test.
     */
    @Before
    public void init() {
        this.dictionary = new Dictionary();
    }

    /**
     * Test about chained dictionaries.
     */
    @Test
    public void testChainedDictionaries() {
        dictionary.put(
                "names",
                new Dictionary(() -> new Iterator<>() {
                    private int index = 0;

                    @Override
                    public boolean hasNext() {
                        return index < 10;
                    }

                    @Override
                    public Tuple next() {
                        return new Tuple(index, "Name Example" + index++);
                    }
                })
        );

        Dictionary innerDictionary = (Dictionary) dictionary.get("names");
        assertEquals(
                "Wrong size of inner dictionary",
                10, innerDictionary.size()
        );

        for (int i = 0; i < 10; i++) {
            assertEquals(
                    "Wrong value from inner dictionary",
                    "Name Example" + i, ((Dictionary) dictionary.get("names")).get(i)
            );
        }
    }

    /**
     * Test about dictionary with an inner tuple as value.
     */
    @Test
    public void testSeveralItemsDictionary1() {
        Tuple tuple = new Tuple(1, 2, 3);
        dictionary.put("test", tuple);

        assertEquals(
                "Wrong string from dictionary with tuple",
                "{'test': (1, 2, 3)}", dictionary.toString()
        );

        assertEquals(
                "Dictionary inner tuple must be equal to previous tuple",
                tuple, dictionary.get("test")
        );
    }

    /**
     * Test about dictionary with inner array as value.
     */
    @Test
    public void testSeveralItemsDictionary2() {
        Integer[] integers = new Integer[]{1, 2, 3};
        dictionary.put("test", integers);

        assertEquals(
                "Wrong string from dictionary with array",
                "{'test': [1, 2, 3]}", dictionary.toString()
        );

        Integer[] innerArray = (Integer[]) dictionary.get("test");
        for (int i = 0; i < 3; i++) {
            assertEquals(
                    "Wrong value from inner array",
                    i + 1, (Object) innerArray[i]
            );
            innerArray[i] = (i + 1) * 100;
        }

        for (int i = 0; i < 3; i++) {
            assertEquals(
                    "Wrong value from inner array after altering it",
                    (i + 1) * 100, (Object) innerArray[i]
            );
        }
    }

    /**
     * Test about dictionary with inner tuple containing another dictionary.
     */
    @Test
    public void testSeveralItemsDictionary3() {
        Tuple otherTuple = new Tuple(
                new Dictionary(
                        1, 2, 3, 4
                ), "test"
        );
        dictionary.put("test", otherTuple);

        assertEquals(
                "Wrong string from dictionary with tuple containing dictionary",
                "{'test': ({1: 2, 3: 4}, 'test')}", dictionary.toString()
        );

        Dictionary otherDictionary = (Dictionary) ((Tuple) dictionary.get("test")).get(0);
        for (int i = 1; i < 4; i += 2) {
            assertEquals(
                    "Wrong value from dictionary into inner tuple value",
                    i + 1, otherDictionary.get(i)
            );
        }

        ((Dictionary) otherTuple.get(0)).put(1, "test");

        assertEquals(
                "Wrong value from dictionary into inner tuple value" +
                        " after altering its values",
                "test", otherDictionary.get(1)
        );
    }
}
