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
 * @version 15-03-2024
 * @since 15-03-2024
 */
public class TestEmptyDictionary {
    private Dictionary dictionary;

    @Before
    public void init() {
        this.dictionary = new Dictionary();
    }

    @Test
    public void testLen()  {
        int dictionaryLen = dictionary.size();
        assertEquals(
                "Length must be 0 after initializing empty dictionary",
                0, dictionaryLen
        );
    }

    @Test
    public void testPut1() {
        dictionary.put("1", 1);
        int dictionaryLen = dictionary.size();
        assertEquals(
                "Wrong size after inserting new pair into empty dictionary",
                1, dictionaryLen
        );
    }

    @Test
    public void testPut2() {
        dictionary.put("1", 1);
        Object value = dictionary.get("1");
        assertEquals(
                "Wrong value after inserting new pair into empty dictionary",
                1, value
        );
    }

    @Test
    public void testPut3() {
        dictionary.put("1", 1);
        Object value = dictionary.setDefault("1");
        assertEquals(
                "Wrong value after inserting new pair into empty dictionary",
                1, value
        );
    }

    @Test
    public void testPut4() {
        dictionary.put("1", 1);
        assertTrue(
                "True expected",
                dictionary.containsKey("1")
        );
    }

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

    @Test
    public void testUpdate3() throws EmptyDictionaryException {
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

    @Test
    public void testCopy1() {
        Dictionary dictionaryCopy = dictionary.copy();
        assertTrue(
                "True expected",
                dictionary.equals(dictionaryCopy)
        );
    }

    @Test
    public void testCopy2() {
        Dictionary dictionaryCopy = dictionary.copy();
        int dictionaryCopyLength = dictionaryCopy.size();
        assertEquals(
                "Wrong size after copying empty dictionary",
                0, dictionaryCopyLength

        );
    }

    @Test
    public void testSetDefault1() {
        Object value = dictionary.setDefault("Test");
        assertEquals(
                "Wrong value",
                null, value
        );
    }

    @Test
    public void testSetDefault2() {
        Object value = dictionary.setDefault("Test", 10);
        assertEquals(
                "Wrong value",
                10, value
        );
    }

    @Test
    public void testSetDefault3() {
        dictionary.setDefault("Test");
        Object value = dictionary.get("Test");
        assertEquals(
                "Wrong value",
                null, value
        );
    }

    @Test
    public void testSetDefault4() {
        dictionary.setDefault("Test", 10);
        Object value = dictionary.get("Test");
        assertEquals(
                "Wrong value",
                10, value
        );
    }

    @Test
    public void testGet() {
        Object value = dictionary.get("Test", 100);
        assertEquals(
                "Wrong value",
                100, value
        );
    }

    @Test
    public void testString()  {
        String str = dictionary.toString();
        assertEquals(
                "Wrong representation",
                "{}", str
        );
    }

    @Test
    public void testKeys() {
        String keys = dictionary.keys().toString();
        assertEquals(
                "Dictionary's keys string does not match the expected",
                "DictionaryKeys([])", keys
        );
    }

    @Test
    public void testValues() {
        String values = dictionary.values().toString();
        assertEquals(
                "Dictionary's values string does not match the expected",
                "DictionaryValues([])", values
        );
    }

    @Test
    public void testItems() {
        String items = dictionary.items().toString();
        assertEquals(
                "Dictionary's items string does not match the expected",
                "DictionaryItems([])", items
        );
    }

    @Test
    public void testContainsKey() {
        assertFalse(
                "False expected",
                dictionary.containsKey("key")
        );
    }

    @Test
    public void testEquals1() {
        Dictionary otherDictionary = new Dictionary();
        assertTrue(
                "True expected after comparing dictionary copy",
                dictionary.equals(otherDictionary)
        );
    }

    @Test
    public void testEquals2() {
        assertTrue(
                "True expected after comparing self dictionary",
                dictionary.equals(dictionary)
        );
    }

    @Test
    public void testEquals3() {
        Dictionary otherDictionary = new Dictionary();
        otherDictionary.put(1, 1);
        assertFalse(
                "False expected after comparing non equal dictionary",
                dictionary.equals(otherDictionary)
        );
    }

    @Test(expected = KeyErrorException.class)
    public void testGetNoneExistingKey() throws KeyErrorException {
        dictionary.getItem("1");
    }

    @Test
    public void testKeyErrorException1() {
        try {
            dictionary.getItem("1");
        } catch (KeyErrorException err) {
            assertEquals(
                    "Wrong exception value",
                    "1", err.value()
            );
        }

    }

    @Test(expected = KeyErrorException.class)
    public void tesPop1() throws KeyErrorException {
        dictionary.pop("1");
    }

    @Test
    public void tesPop2() throws KeyErrorException {
        try {
            dictionary.pop("1");
        } catch (KeyErrorException err) {
            assertEquals(
                    "The key that caused the exception does not" +
                            "match the one located in the exception",
                    "1", err.value()
            );
        }
    }

    @Test(expected = EmptyDictionaryException.class)
    public void testPopItem() throws EmptyDictionaryException {
        dictionary.popItem();
    }
}
