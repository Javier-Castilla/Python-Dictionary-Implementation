package org.ulpgc.edp.tests;

import org.junit.*;
import org.ulpgc.edp.exceptions.*;
import org.ulpgc.edp.model.dct.Dictionary;
import org.ulpgc.edp.model.tpl.Tuple;
import static org.junit.Assert.*;

/**
 * Testing class for test a Dictionary with many items.
 *
 * @author Javier Castilla
 * @version 22-03-2024
 */
public class TestManyItemsDictionary {
    private Dictionary dictionary;
    private Object[] keys;
    private Object[] items;

    /**
     * Initial state of every test.
     */
    @Before
    public void init() {
        this.dictionary = new Dictionary();
        this.keys = new Object[32];
        this.items = new Object[64];
        int index = 0;
        for (int i = 0; i < 32; i++) {
            keys[i] = i;
            items[index++] = i;
            items[index++] = i;
            dictionary.put(i, i);
        }
    }

    /**
     * Test about size of initialize dictionary with 32 elements.
     */
    @Test
    public void testLen() {
        int dictionaryLength = dictionary.size();
        assertEquals(
                "Wrong size after initializing dictionary with 32 elements",
                32, dictionaryLength
        );
    }

    /**
     * Test about many items dictionary put method. Checks new added value.
     */
    @Test
    public void testPut() {
        dictionary.put("Test", 10);
        int dictionaryLength = dictionary.size();
        assertEquals(
                "Wrong size after inserting new pair",
                33, dictionaryLength
        );
    }

    /**
     * Test about many items dictionary popItem method. Checks deleted item.
     */
    @Test
    public void testPopItem1() throws KeyErrorException {
        Tuple item = dictionary.popItem();
        assertEquals(
                "Wrong last inserted value",
                new Tuple(31, 31), item
        );
    }

    /**
     * Test about many items dictionary popItem method. Checks size after deleting.
     */
    @Test
    public void testPopItem2() throws KeyErrorException {
        dictionary.popItem();
        assertEquals(
                "Wrong size after deleting last introduced item",
                31, dictionary.size()
        );
    }

    /**
     * Test about many items dictionary popItem method. Checks if value has been deleted.
     */
    @Test
    public void testPopItem3() throws KeyErrorException {
        dictionary.popItem();
        assertEquals(
                "Last element has not been deleted",
                null, dictionary.get(31)
        );
    }

    /**
     * Test about many items dictionary put method. Checks size after replacing.
     */
    @Test
    public void testReplace1() {
        dictionary.put(17, 100);
        int dictionaryLength = dictionary.size();
        assertEquals(
                "Wrong size after updating pair",
                32, dictionaryLength
        );
    }

    /**
     * Test about many items dictionary put method. Checks replaced item value.
     */
    @Test
    public void testReplace2() {
        dictionary.put(17, 100);
        Object value = dictionary.get(17);
        assertEquals(
                "Pair has not been correctly updated",
                100, value
        );
    }

    /**
     * Test about many items dictionary put method. Checks if added value is not
     * the last introduced.
     */
    @Test
    public void testReplace3() throws KeyErrorException {
        dictionary.put(17, 100);
        Object value = dictionary.popItem();
        assertEquals(
                "Wrong last inserted value after updating pair",
                new Tuple(31, 31), value
        );
    }

    /**
     * Test about many items dictionary put method. Checks returned value when
     * deleting replaced item.
     */
    @Test
    public void testReplace4() throws KeyErrorException {
        dictionary.put(17, 100);
        Object value = dictionary.pop(17);
        assertEquals(
                "Wrong value after updating pair",
                100, value
        );
    }

    /**
     * Test about many items dictionary clear method. Checks size after clearing.
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
     * Test about many items dictionary clear method. Checks is exception is thrown
     * when using popItem upon cleared dictionary.
     */
    @Test(expected = KeyErrorException.class)
    public void testClear2() throws KeyErrorException {
        dictionary.clear();
        dictionary.popItem();
    }

    /**
     * Test about many items dictionary clear method. Checks dictionary string
     * after clearing.
     */
    @Test
    public void testClear3() {
        dictionary.clear();
        String str = dictionary.toString();
        assertEquals(
                "Wrong string representation after clearing",
                "{}", str
        );
    }

    /**
     * Test about many items dictionary clear method. Checks keys string
     * after clearing.
     */
    @Test
    public void testClear4() {
        dictionary.clear();
        String str = dictionary.keys().toString();
        assertEquals(
                "Wrong keys string representation after clearing",
                "DictionaryKeys([])", str
        );
    }

    /**
     * Test about many items dictionary clear method. Checks values string
     * after clearing.
     */
    @Test
    public void testClear5() {
        dictionary.clear();
        String str = dictionary.values().toString();
        assertEquals(
                "Wrong values string representation after clearing",
                "DictionaryValues([])", str
        );
    }

    /**
     * Test about many items dictionary clear method. Checks items string
     * after clearing.
     */
    @Test
    public void testClear6() {
        dictionary.clear();
        String str = dictionary.items().toString();
        assertEquals(
                "Wrong items string representation after clearing",
                "DictionaryItems([])", str
        );
    }

    /**
     * Test about many items dictionary equals method. Checks other dictionary equality.
     */
    @Test
    public void testEquals1() {
        Dictionary otherDictionary = new Dictionary();
        for (int i = 0; i < 32; i++) {
            otherDictionary.put(i, i);
        }

        assertTrue(
                "Wrong value after comparing equals dictionaries",
                dictionary.equals(otherDictionary)
        );
    }

    /**
     * Test about many items dictionary equals method. Checks self dictionary equality.
     */
    @Test
    public void testEquals2() {
        assertTrue(
                "Wrong value after comparing self dictionary",
                dictionary.equals(dictionary)
        );
    }

    /**
     * Test about many items dictionary equals method. Checks non-equal dictionary.
     */
    @Test
    public void testEquals3() {
        Dictionary otherDictionary = new Dictionary();
        otherDictionary.put(1, 1);
        assertFalse(
                "Wrong value after comparing not equals dictionary",
                dictionary.equals(otherDictionary)
        );
    }

    /**
     * Test about many items dictionary equals method. Checks copy dictionary equality.
     */
    @Test
    public void testEquals4() {
        Dictionary otherDictionary = dictionary.copy();
        assertTrue(
                "Wrong value after comparing dictionary copy",
                dictionary.equals(otherDictionary)
        );
    }

    /**
     * Test about many items dictionary fromKeys method. Checks default added values.
     */
    @Test
    public void testFromKeys1() {
        Dictionary newDictionary = Dictionary.fromKeys(new Tuple(items));
        for (int i = 0; i < 32; i++) {
            assertEquals(
                    "Wrong value from keys dictionary",
                    null, newDictionary.get(i)
            );
        }
    }

    /**
     * Test about many items dictionary fromKeys method. Checks default added values.
     */
    @Test
    public void testFromKeys2() {
        Dictionary newDictionary = Dictionary.fromKeys(
                new Tuple(keys), "test"
        );

        for (int i = 0; i < 32; i++) {
            assertEquals(
                    "Wrong value from keys dictionary",
                    "test", newDictionary.get(i)
            );
        }
    }

    /**
     * Test about many items dictionary fromKeys method. Checks size.
     */
    @Test
    public void testFromKeys3() {
        Dictionary newDictionary = Dictionary.fromKeys(new Tuple(items));
        assertEquals(
                "Wrong size from keys dictionary",
                32, newDictionary.size()
        );
    }

    /**
     * Test about many items dictionary fromKeys method. Checks size.
     */
    @Test
    public void testFromKeys4() {
        Dictionary newDictionary = Dictionary.fromKeys(
                new Tuple(keys), "test"
        );

        assertEquals(
                "Wrong size from keys dictionary",
                32, newDictionary.size()
        );
    }

    /**
     * Test about many items dictionary fromKeys method. Checks if values
     * of the dictionary where keys were taken are contained in the dictionary.
     */
    public void testFromKeys5() {
        Dictionary newDictionary = Dictionary.fromKeys(dictionary.keys());
        for (Object key : dictionary.keys()) {
            assertTrue(
                    "From keys dictionary does not have expected keys",
                    newDictionary.containsKey(key)
            );
        }
    }

    /**
     * Test about many items dictionary fromKeys method. Checks if values
     * of the dictionary where keys were taken are contained in the dictionary.
     */
    @Test
    public void testFromKeys6() {
        Dictionary newDictionary = Dictionary.fromKeys(
                dictionary.keys(), "Test"
        );
        for (Object key : dictionary.keys()) {
            assertEquals(
                    "From keys dictionary does not have expected items",
                    "Test", newDictionary.get(key)
            );
        }
    }

    /**
     * Test about many items dictionary setDefault method. Checks added or existing value.
     */
    @Test
    public void setDefault1() {
        Object value = dictionary.setDefault(10);
        assertEquals(
                "Wrong value",
                10, value
        );
    }

    /**
     * Test about many items dictionary setDefault method. Checks added or existing value.
     */
    @Test
    public void setDefault2() {
        Object value = dictionary.setDefault(10, "Test");
        assertEquals(
                "Wrong value",
                10, value
        );
    }

    /**
     * Test about many items dictionary setDefault method. Checks default added value.
     */
    @Test
    public void setDefault3() {
        Object value = dictionary.setDefault(32);
        assertEquals(
                "Wrong value",
                null, value
        );
    }

    /**
     * Test about many items dictionary setDefault method. Checks default added value.
     */
    @Test
    public void setDefault4() {
        Object value = dictionary.setDefault(32, "Test");
        assertEquals(
                "Wrong value",
                "Test", value
        );
    }
}
