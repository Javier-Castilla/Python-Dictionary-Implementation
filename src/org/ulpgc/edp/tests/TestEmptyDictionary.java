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
 * @version 22-03-2024
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
     * Test about empty dictionary put method. Checks size.
     */
    @Test
    public void testPut1() {
        dictionary.put("1", 1);
        int dictionaryLen = dictionary.size();
        assertEquals(
                "Wrong size after inserting new pair into empty dictionary",
                1, dictionaryLen
        );
    }

    /**
     * Test about empty dictionary put method. Checks added value.
     */
    @Test
    public void testPut2() {
        dictionary.put("1", 1);
        Object value = dictionary.get("1");
        assertEquals(
                "Wrong value after inserting new pair into empty dictionary",
                1, value
        );
    }

    /**
     * Test about empty dictionary put method. Checks added value with
     * setDefault method.
     */
    @Test
    public void testPut3() {
        dictionary.put("1", 1);
        Object value = dictionary.setDefault("1");
        assertEquals(
                "Wrong value after inserting new pair into empty dictionary",
                1, value
        );
    }

    /**
     * Test about empty dictionary put method. Checks if dictionary contains
     * added key - value.
     */
    @Test
    public void testPut4() {
        dictionary.put("1", 1);
        assertTrue(
                "True expected",
                dictionary.containsKey("1")
        );
    }

    /**
     * Test about empty dictionary update method. Checks size.
     */
    @Test
    public void testUpdate1() {
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
    }

    /**
     * Test about empty dictionary update method. Checks dictionary string.
     */
    @Test
    public void testUpdate2() {
        Dictionary otherDictionary = new Dictionary();
        for (int i = 0; i < 4; i++) {
            otherDictionary.put(i, i);
        }
        dictionary.update(otherDictionary);
        String str = dictionary.toString();
        assertEquals(
                "Wrong values after updating empty dictionary",
                "{0: 0, 1: 1, 2: 2, 3: 3}", str
        );
    }

    /**
     * Test about empty dictionary update method. Checks popItem returned value.
     */
    @Test
    public void testUpdate3() throws KeyErrorException {
        Dictionary otherDictionary = new Dictionary();
        for (int i = 0; i < 4; i++) {
            otherDictionary.put(i, i);
        }
        dictionary.update(otherDictionary);
        Tuple value = dictionary.popItem();
        assertEquals(
                "Wrong last item",
                new Tuple(3, 3), value
        );
    }

    /**
     * Test about empty dictionary copy method. Checks dictionaries equality.
     */
    @Test
    public void testCopy1() {
        Dictionary dictionaryCopy = dictionary.copy();
        assertTrue(
                "True expected",
                dictionary.equals(dictionaryCopy)
        );
    }

    /**
     * Test about empty dictionary copy method. Checks size.
     */
    @Test
    public void testCopy2() {
        Dictionary dictionaryCopy = dictionary.copy();
        int dictionaryCopyLength = dictionaryCopy.size();
        assertEquals(
                "Wrong size after copying empty dictionary",
                0, dictionaryCopyLength

        );
    }

    /**
     * Test about empty dictionary setDefault method. Checks returned value
     * from inserting non-existing pair.
     */
    @Test
    public void testSetDefault1() {
        Object value = dictionary.setDefault("Test");
        assertEquals(
                "Wrong value",
                null, value
        );
    }

    /**
     * Test about empty dictionary setDefault method. Checks returned value
     * from inserting non-existing pair.
     */
    @Test
    public void testSetDefault2() {
        Object value = dictionary.setDefault("Test", 10);
        assertEquals(
                "Wrong value",
                10, value
        );
    }

    /**
     * Test about empty dictionary get method. Checks default value if
     * key is not contained.
     */
    @Test
    public void testGet1() {
        Object value = dictionary.get("Test", 100);
        assertEquals(
                "Wrong value",
                100, value
        );
    }

    /**
     * Test about empty dictionary get method. Checks default value if
     * key is not contained.
     */
    @Test
    public void testGet2() {
        Object value = dictionary.get("Test");
        assertEquals(
                "Wrong value",
                null, value
        );
    }

    /**
     * Test about empty dictionary keys method. Checks keys string.
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
     * Test about empty dictionary values method. Checks values string.
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
     * Test about empty dictionary items method. Checks items string.
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
     * Test about empty dictionary containsKey method. Checks a non-existing key.
     */
    @Test
    public void testContainsKey1() {
        assertFalse(
                "False expected",
                dictionary.containsKey("key")
        );
    }

    /**
     * Test about empty dictionary equals method. Checks equality with
     * dictionary copy.
     */
    @Test
    public void testEquals1() {
        Dictionary otherDictionary = new Dictionary();
        assertTrue(
                "True expected after comparing dictionary copy",
                dictionary.equals(otherDictionary)
        );
    }

    /**
     * Test about empty dictionary equals method. Checks equality with
     * self dictionary.
     */
    @Test
    public void testEquals2() {
        assertTrue(
                "True expected after comparing self dictionary",
                dictionary.equals(dictionary)
        );
    }

    /**
     * Test about empty dictionary equals method. Checks equality with
     * non-equal dictionary.
     */
    @Test
    public void testEquals3() {
        Dictionary otherDictionary = new Dictionary();
        otherDictionary.put(1, 1);
        assertFalse(
                "False expected after comparing non equal dictionary",
                dictionary.equals(otherDictionary)
        );
    }

    /**
     * Test about empty dictionary getItem method. Checks if exception is thrown.
     */
    @Test(expected = KeyErrorException.class)
    public void testGetNoneExistingKey() throws KeyErrorException {
        dictionary.getItem("1");
    }

    /**
     * Test about empty dictionary pop method. Checks if exception is thrown.
     */
    @Test(expected = KeyErrorException.class)
    public void tesPop() throws KeyErrorException {
        dictionary.pop("1");
    }

    /**
     * Test about empty dictionary popItem method. Checks if exception is thrown.
     */
    @Test(expected = KeyErrorException.class)
    public void testPopItem() throws KeyErrorException {
        dictionary.popItem();
    }
}
