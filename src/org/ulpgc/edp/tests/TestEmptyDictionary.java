package org.ulpgc.edp.tests;

import org.junit.*;
import org.ulpgc.edp.exceptions.*;
import org.ulpgc.edp.model.dct.Dictionary;
import org.ulpgc.edp.model.tpl.Tuple;
import static org.junit.Assert.*;

/**
 * Testing class for test an empty Dictionary.
 *
 * @author Javier Castilla
 * @version 28-03-2024
 */
public class TestEmptyDictionary {
    private Dictionary dictionary;

    /**
     * Initial state of every test.
     */
    @Before
    public void init() {
        this.dictionary = new Dictionary();
    }

    /**
     * Test about empty dictionary size.
     */
    @Test
    public void testLen()  {
        int dictionaryLen = dictionary.size();
        assertEquals(
                "Length must be 0 after initializing empty dictionary",
                0, dictionaryLen
        );
    }

    /**
     * Test about empty dictionary put method.
     */
    @Test
    public void testPut() {
        dictionary.put("1", 1);
        int dictionaryLen = dictionary.size();

        assertEquals(
                "Wrong size after inserting new pair into empty dictionary",
                1, dictionaryLen
        );

        Object value = dictionary.get("1");
        assertEquals(
                "Wrong value after inserting new pair into empty dictionary",
                1, value
        );

        value = dictionary.setDefault("1");
        assertEquals(
                "Wrong value after inserting new pair into empty dictionary",
                "1", value
        );

        assertTrue(
                "True expected",
                dictionary.containsKey("1")
        );
    }

    /**
     * Test about empty dictionary update method.
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
                "Wrong size after updating empty dictionary",
                4, dictionaryLength
        );

        assertEquals(
                "Wrong values after updating empty dictionary",
                "{0: 0, 1: 1, 2: 2, 3: 3}", dictionary.toString()
        );

        Tuple value = dictionary.popItem();
        assertEquals(
                "Wrong last item",
                new Tuple(3, 3), value
        );
    }

    /**
     * Test about empty dictionary copy method.
     */
    @Test
    public void testCopy() {
        Dictionary dictionaryCopy = dictionary.copy();

        int dictionaryCopyLength = dictionaryCopy.size();
        assertEquals(
                "Wrong size after copying empty dictionary",
                0, dictionaryCopyLength

        );

        assertEquals("True expected",
                dictionary, dictionaryCopy
        );
    }

    /**
     * Test about empty dictionary setDefault method.
     */
    @Test
    public void testSetDefault() {
        Object value = dictionary.setDefault("Test");
        assertNull("Wrong value", value);

        dictionary.pop("Test");

        value = dictionary.setDefault("Test", 10);
        assertEquals(
                "Wrong value",
                10, value
        );
    }

    /**
     * Test about empty dictionary get method.
     */
    @Test
    public void testGet() {
        Object value = dictionary.get("Test", 100);
        assertEquals(
                "Wrong value",
                100, value
        );

        value = dictionary.get("Test");
        assertNull("Wrong value", value);
    }

    /**
     * Test about empty dictionary keys method.
     */
    @Test
    public void testKeys() {
        String keys = dictionary.keys().toString();
        assertEquals(
                "Dictionary's keys string does not match the expected",
                "DictionaryKeys([])", keys
        );
    }

    /**
     * Test about empty dictionary values method.
     */
    @Test
    public void testValues() {
        String values = dictionary.values().toString();
        assertEquals(
                "Dictionary's values string does not match the expected",
                "DictionaryValues([])", values
        );
    }

    /**
     * Test about empty dictionary items method.
     */
    @Test
    public void testItems() {
        String items = dictionary.items().toString();
        assertEquals(
                "Dictionary's items string does not match the expected",
                "DictionaryItems([])", items
        );
    }

    /**
     * Test about empty dictionary containsKey method.
     */
    @Test
    public void testContainsKey() {
        assertFalse(
                "False expected",
                dictionary.containsKey("key")
        );
    }

    /**
     * Test about empty dictionary equals method.
     * dictionary copy.
     */
    @Test
    public void testEquals() {
        Dictionary otherDictionary = new Dictionary();
        assertEquals(
                "True expected after comparing dictionary copy",
                dictionary, otherDictionary
        );

        assertEquals(
                "True expected after comparing self dictionary",
                dictionary, dictionary
        );

        otherDictionary.put(1, 1);
        assertNotEquals(
                "False expected after comparing non equal dictionary",
                dictionary, otherDictionary
        );
    }

    /**
     * Test about empty dictionary getItem method.
     */
    @Test(expected = KeyError.class)
    public void testGetNoneExistingKey() throws KeyError {
        dictionary.getItem("1");
    }

    /**
     * Test about empty dictionary pop method.
     */
    @Test(expected = KeyError.class)
    public void tesPopNonExistingKey() throws KeyError {
        dictionary.pop("1");
    }

    /**
     * Test about empty dictionary popItem method.
     */
    @Test(expected = KeyError.class)
    public void testPopItem() throws KeyError {
        dictionary.popItem();
    }
}
