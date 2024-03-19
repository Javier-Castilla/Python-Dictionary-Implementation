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
 * @version 15-03-2024
 * @since 15-03-2024
 */
public class TestOneItemDictionary {
    private Dictionary dictionary;
    @Before
    public void init() {
        this.dictionary = new Dictionary();
        dictionary.put("1", 1);
    }

    @Test
    public void testPutAndPop1() {
        dictionary.put("2", 2);
        int len = dictionary.size();
        assertEquals(
                "Wrong size",
                2, len
        );
    }

    @Test
    public void testPutAndPop2() throws KeyErrorException {
        dictionary.put("2", 2);
        dictionary.pop("2");
        int len = dictionary.size();
        assertEquals(
                "Wrong size",
                1, len
        );
    }

    @Test
    public void testPutAndPop3() throws EmptyDictionaryException {
        dictionary.put("2", 2);
        Tuple item = dictionary.popitem();
        assertEquals(
                "Wrong value",
                new Tuple("2", 2), item
        );
    }

    @Test
    public void testPutAndPop4() throws KeyErrorException {
        dictionary.put("2", 2);
        Object value = dictionary.pop("2");
        assertEquals(
                "Wrong value",
                2, value
        );
    }

    @Test
    public void testPutAndPop5() throws KeyErrorException {
        dictionary.put("2", 2);
        Object value = dictionary.pop("2");
        assertEquals(
                "Wrong value",
                2, value
        );
    }

    @Test
    public void testReplace1() {
        dictionary.put("1", 100);
        int dictionaryLength = dictionary.size();
        assertEquals(
                "Wrong size",
                1, dictionaryLength
        );
    }

    @Test
    public void testReplace2() throws KeyErrorException {
        dictionary.put("1", 100);
        Object value = dictionary.get("1");
        assertEquals(
                "Wrong value",
                100, value
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
                "Wrong size",
                5, dictionaryLength
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
                "The elements are not correct ",
                "{'1': 1, 0: 0, 1: 1, 2: 2, 3: 3}", str
        );
    }

    @Test
    public void testString()  {
        String str = dictionary.toString();
        assertEquals(
                "Representations is not correct ",
                "{'1': 1}", str
        );
    }

    @Test
    public void testClear1() {
        dictionary.clear();
        int dictionaryLength = dictionary.size();
        assertEquals(
                "Wrong size",
                0, dictionaryLength
        );
    }

    @Test(expected = EmptyDictionaryException.class)
    public void testClear2() throws EmptyDictionaryException {
        dictionary.clear();
        dictionary.popitem();
    }

    @Test
    public void testKeys() {
        String keys = dictionary.keys().toString();
        assertEquals(
                "Returned keys do not match  " +
                        "with the expected. ",
                "DictionaryKeys(['1'])", keys
        );
    }

    @Test
    public void testValues() {
        String values = dictionary.values().toString();
        assertEquals(
                "Returned values do not match  " +
                        "with the expected. ",
                "DictionaryValues([1])", values
        );
    }

    @Test
    public void testItems() {
        String items = dictionary.items().toString();
        assertEquals(
                "Returned pairs do not match  " +
                        "with the expected. ",
                "DictionaryItems([('1', 1)])", items
        );
    }

    @Test
    public void testContainsKey() {
        assertTrue(
                "Wrong value",
                dictionary.containsKey("1")
        );
    }

    @Test
    public void testPopItem1() throws EmptyDictionaryException {
        Tuple values = dictionary.popitem();
        assertEquals(
                "Wrong value",
                new Tuple("1", 1), values
        );
    }

    @Test
    public void testPopItem2() throws EmptyDictionaryException {
        dictionary.popitem();
        int dictionaryLength = dictionary.size();
        assertEquals(
                "Wrong size",
                0, dictionaryLength
        );
    }

    @Test(expected = KeyErrorException.class)
    public void testGetNoneExistingKey() throws KeyErrorException {
        dictionary.getItem("2");
    }

    @Test
    public void tesPop() throws KeyErrorException {
        Object value = dictionary.pop("1");
        assertEquals(
                "The element when removing a pair key - value " +
                        "is not correct",
                1, value
        );
    }

    @Test
    public void testEquals1() {
        Dictionary otherDictionary = new Dictionary();
        otherDictionary.put("1", 1);
        assertTrue(
                "Wrong value",
                dictionary.equals(otherDictionary)
        );
    }

    @Test
    public void testEquals2() {
        assertTrue(
                "Wrong value",
                dictionary.equals(dictionary)
        );
    }

    @Test
    public void testEquals3() {
        Dictionary otherDictionary = new Dictionary();
        otherDictionary.put(1, 1);
        assertFalse(
                "Wrong value",
                dictionary.equals(otherDictionary)
        );
    }
}
