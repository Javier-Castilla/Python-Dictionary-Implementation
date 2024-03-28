package org.ulpgc.edp.tests;

import org.junit.*;
import org.ulpgc.edp.exceptions.*;
import org.ulpgc.edp.model.dct.Dictionary;
import org.ulpgc.edp.model.tpl.Tuple;
import static org.junit.Assert.*;

/**
 * Testing class for test a Dictionary with one element.
 *
 * @author Javier Castilla
 * @version 28-03-2024
 */
public class TestOneItemDictionary {
    private Dictionary dictionary;

    /**
     * Initial state of every test.
     */
    @Before
    public void init() {
        this.dictionary = new Dictionary();
        dictionary.put("1", 1);
    }

    /**
     * Test about one item dictionary put method.
     */
    @Test
    public void testPut() {
        dictionary.put("2", 2);
        assertEquals(
                "Wrong size",
                2, dictionary.size()
        );

        Tuple item = dictionary.popItem();
        assertEquals(
                "Wrong value",
                new Tuple("2", 2), item
        );
    }

    /**
     * Test about one item dictionary pop method.
     */
    @Test
    public void testPop() {
        Object value = dictionary.pop("1");
        assertEquals(
                "Wrong size",
                0, dictionary.size()
        );

        assertEquals(
                "Wrong value",
                1, value
        );
    }

    /**
     * Test about one item dictionary put method when key is already in the dictionary.
     */
    @Test
    public void testReplace() {
        dictionary.put("1", 100);
        int dictionaryLength = dictionary.size();
        assertEquals(
                "Wrong size",
                1, dictionaryLength
        );

        Object value = dictionary.get("1");
        assertEquals(
                "Wrong value",
                100, value
        );
    }

    /**
     * Test about one item dictionary setDefault method.
     */
    @Test
    public void testUpdate() {
        Dictionary otherDictionary = new Dictionary();
        for (int i = 0; i < 4; i++) {
            otherDictionary.put(i, i);
        }

        dictionary.update(otherDictionary);
        int dictionaryLength = dictionary.size();
        assertEquals(
                "Wrong size",
                5, dictionaryLength
        );

        assertEquals(
                "The elements are not correct ",
                "{'1': 1, 0: 0, 1: 1, 2: 2, 3: 3}", dictionary.toString()
        );
    }

    /**
     * Test about one item dictionary string.
     */
    @Test
    public void testString()  {
        String str = dictionary.toString();
        assertEquals(
                "Representations is not correct ",
                "{'1': 1}", str
        );
    }

    /**
     * Test about one item dictionary clear method.
     */
    @Test
    public void testClear() {
        dictionary.clear();
        int dictionaryLength = dictionary.size();
        assertEquals(
                "Wrong size",
                0, dictionaryLength
        );
    }

    /**
     * Test about one item dictionary keys string method.
     */
    @Test
    public void testKeys() {
        String keys = dictionary.keys().toString();
        assertEquals(
                "Returned keys do not match  " +
                        "with the expected. ",
                "DictionaryKeys(['1'])", keys
        );
    }

    /**
     * Test about one item dictionary values string method.
     */
    @Test
    public void testValues() {
        String values = dictionary.values().toString();
        assertEquals(
                "Returned values do not match  " +
                        "with the expected. ",
                "DictionaryValues([1])", values
        );
    }

    /**
     * Test about one item dictionary items string method.
     */
    @Test
    public void testItems() {
        String items = dictionary.items().toString();
        assertEquals(
                "Returned pairs do not match  " +
                        "with the expected. ",
                "DictionaryItems([('1', 1)])", items
        );
    }

    /**
     * Test about one item dictionary containsKey method.
     */
    @Test
    public void testContainsKey() {
        assertTrue(
                "Wrong value with contained value",
                dictionary.containsKey("1")
        );

        assertFalse(
                "Wrong value with not-contained value",
                dictionary.containsKey("test")
        );
    }

    /**
     * Test about one item dictionary popItem method.
     */
    @Test
    public void testPopItem() throws KeyError {
        Tuple values = dictionary.popItem();
        assertEquals(
                "Wrong value",
                new Tuple("1", 1), values
        );

        assertEquals(
                "Wrong size",
                0, dictionary.size()
        );
    }

    /**
     * Test about one item dictionary getItem method. Checks if exception is
     * thrown with non-existing key.
     */
    @Test(expected = KeyError.class)
    public void testGetNoneExistingKey() throws KeyError {
        dictionary.getItem("2");
    }

    /**
     * Test about one item dictionary getItem method.
     */
    @Test
    public void testGetItem() {
        Object value = dictionary.get("1");
        assertEquals(
                "Wrong value with contained element",
                1, value
        );
    }

    /**
     * Test about one item dictionary equals method.
     */
    @Test
    public void testEquals() {
        Dictionary otherDictionary = new Dictionary();
        otherDictionary.put("1", 1);
        assertEquals("Wrong value", dictionary, otherDictionary);
        otherDictionary.clear();

        assertEquals("Wrong value", dictionary, dictionary);

        otherDictionary.put(1, 1);
        assertNotEquals("Wrong value", dictionary, otherDictionary);
    }
}
