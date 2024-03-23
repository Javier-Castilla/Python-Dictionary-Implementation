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
 * @version 22-03-2024
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
     * Test about one item dictionary put and pop method. Checks size after
     * adding item.
     */
    @Test
    public void testPutAndPop1() {
        dictionary.put("2", 2);
        int len = dictionary.size();
        assertEquals(
                "Wrong size",
                2, len
        );
    }

    /**
     * Test about one item dictionary put and pop method. Checks size after
     * doing pop.
     */
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

    /**
     * Test about one item dictionary put and pop method. Checks last item introduced.
     */
    @Test
    public void testPutAndPop3() throws KeyErrorException {
        dictionary.put("2", 2);
        Tuple item = dictionary.popItem();
        assertEquals(
                "Wrong value",
                new Tuple("2", 2), item
        );
    }

    /**
     * Test about one item dictionary put and pop method. Checks last value introduced.
     */
    @Test
    public void testPutAndPop4() throws KeyErrorException {
        dictionary.put("2", 2);
        Object value = dictionary.pop("2");
        assertEquals(
                "Wrong value",
                2, value
        );
    }

    /**
     * Test about one item dictionary put method when key is already in the dictionary.
     * Checks size after replacing.
     */
    @Test
    public void testReplace1() {
        dictionary.put("1", 100);
        int dictionaryLength = dictionary.size();
        assertEquals(
                "Wrong size",
                1, dictionaryLength
        );
    }

    /**
     * Test about one item dictionary put method when key is already in the dictionary.
     * Checks value after replacing.
     */
    @Test
    public void testReplace2() throws KeyErrorException {
        dictionary.put("1", 100);
        Object value = dictionary.get("1");
        assertEquals(
                "Wrong value",
                100, value
        );
    }

    /**
     * Test about one item dictionary setDefault method. Checks size after updating.
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
                "Wrong size",
                5, dictionaryLength
        );
    }

    /**
     * Test about one item dictionary setDefault method. Checks dictionary
     * string after updating.
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
                "The elements are not correct ",
                "{'1': 1, 0: 0, 1: 1, 2: 2, 3: 3}", str
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
     * Test about one item dictionary clear method. Checks size clearing.
     */
    @Test
    public void testClear1() {
        dictionary.clear();
        int dictionaryLength = dictionary.size();
        assertEquals(
                "Wrong size",
                0, dictionaryLength
        );
    }

    /**
     * Test about one item dictionary clear method. Checks if exception is thrown
     * when used popItem.
     */
    @Test(expected = KeyErrorException.class)
    public void testClear2() throws KeyErrorException {
        dictionary.clear();
        dictionary.popItem();
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
     * Test about one item dictionary containsKey method. Checks an existing key.
     */
    @Test
    public void testContainsKey1() {
        assertTrue(
                "Wrong value",
                dictionary.containsKey("1")
        );
    }

    /**
     * Test about one item dictionary containsKey method. Checks a non-existing key.
     */
    @Test
    public void testContainsKey2() {
        assertFalse(
                "Wrong value",
                dictionary.containsKey("test")
        );
    }

    /**
     * Test about one item dictionary popItem method. Checks a returned item.
     */
    @Test
    public void testPopItem1() throws KeyErrorException {
        Tuple values = dictionary.popItem();
        assertEquals(
                "Wrong value",
                new Tuple("1", 1), values
        );
    }

    /**
     * Test about one item dictionary popItem method. Checks size after deleting.
     */
    @Test
    public void testPopItem2() throws KeyErrorException {
        dictionary.popItem();
        int dictionaryLength = dictionary.size();
        assertEquals(
                "Wrong size",
                0, dictionaryLength
        );
    }

    /**
     * Test about one item dictionary getItem method. Checks if exception is
     * thrown with non-existing key.
     */
    @Test(expected = KeyErrorException.class)
    public void testGetNoneExistingKey() throws KeyErrorException {
        dictionary.getItem("2");
    }

    /**
     * Test about one item dictionary pop method. Checks removed value.
     */
    @Test
    public void tesPop() throws KeyErrorException {
        Object value = dictionary.pop("1");
        assertEquals(
                "The element when removing a pair key - value " +
                        "is not correct",
                1, value
        );
    }

    /**
     * Test about one item dictionary equals method. Checks other dictionary equality.
     */
    @Test
    public void testEquals1() {
        Dictionary otherDictionary = new Dictionary();
        otherDictionary.put("1", 1);
        assertTrue(
                "Wrong value",
                dictionary.equals(otherDictionary)
        );
    }

    /**
     * Test about one item dictionary equals method. Checks self dictionary equality.
     */
    @Test
    public void testEquals2() {
        assertTrue(
                "Wrong value",
                dictionary.equals(dictionary)
        );
    }

    /**
     * Test about one item dictionary equals method. Checks copy dictionary equality.
     */
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
