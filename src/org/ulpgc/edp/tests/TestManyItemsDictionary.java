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
 * @version 28-03-2024
 */
public class TestManyItemsDictionary {
    private Dictionary dictionary;
    private Object[] keys;

    /**
     * Initial state of every test.
     */
    @Before
    public void init() {
        this.dictionary = new Dictionary();
        this.keys = new Object[32];
        for (int i = 0; i < 32; i++) {
            keys[i] = i;
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
     * Test about many items dictionary put method.
     */
    @Test
    public void testPut() {
        dictionary.put("Test", 10);
        int dictionaryLength = dictionary.size();
        assertEquals(
                "Wrong size after inserting new pair",
                33, dictionaryLength
        );

        Object value = dictionary.pop("Test");
        assertEquals(
                "Wrong deleted value",
                10, value
        );

        dictionary.put(17, 100);
        dictionaryLength = dictionary.size();
        assertEquals(
                "Wrong size after updating pair",
                32, dictionaryLength
        );

        value = dictionary.get(17);
        assertEquals(
                "Pair has not been correctly updated",
                100, value
        );

        value = dictionary.popItem();
        assertEquals(
                "Wrong last inserted value after updating pair",
                new Tuple(31, 31), value
        );

        value = dictionary.pop(17);
        assertEquals(
                "Wrong value after updating pair",
                100, value
        );
    }

    /**
     * Test about many items dictionary pop method.
     */
    @Test
    public void testPop() {
        for (int i = 0; i < 32; i++) {
            assertEquals(
                    "Wrong value when deleting an element",
                    i, dictionary.pop(i)
            );
        }
    }

    /**
     * Test about many items dictionary get method.
     */
    @Test
    public void testGet() {
        for (int i = 0; i < 32; i++) {
            assertEquals(
                    "Wrong value",
                    i, dictionary.get(i)
            );
        }
    }

    /**
     * Test about many items dictionary popItem method.
     */
    @Test
    public void testPopItem() throws KeyError {
        Tuple item = dictionary.popItem();
        assertEquals(
                "Wrong last inserted value",
                new Tuple(31, 31), item
        );

        assertEquals(
                "Wrong size after deleting last introduced item",
                31, dictionary.size()
        );

        assertNull(
                "Last element has not been deleted",
                dictionary.get(31)
        );
    }

    /**
     * Test about many items dictionary clear method.
     */
    @Test
    public void testClear() {
        dictionary.clear();
        int dictionaryLength = dictionary.size();
        assertEquals(
                "Wrong size",
                0, dictionaryLength
        );

        assertThrows(KeyError.class, () -> dictionary.popItem());

        assertEquals(
                "Wrong string representation after clearing",
                "{}", dictionary.toString()
        );

        assertEquals(
                "Wrong keys string representation after clearing",
                "DictionaryKeys([])", dictionary.keys().toString()
        );

        assertEquals(
                "Wrong values string representation after clearing",
                "DictionaryValues([])", dictionary.values().toString()
        );

        assertEquals(
                "Wrong items string representation after clearing",
                "DictionaryItems([])", dictionary.items().toString()
        );
    }

    /**
     * Test about many items dictionary equals method. Checks other dictionary equality.
     */
    @Test
    public void testEquals() {
        Dictionary otherDictionary = new Dictionary();
        for (int i = 0; i < 32; i++) {
            otherDictionary.put(i, i);
        }

        assertEquals(
                "Wrong value after comparing equals dictionaries",
                dictionary, otherDictionary
        );

        assertEquals(
                "Wrong value after comparing self dictionary",
                dictionary, dictionary
        );

        otherDictionary.clear();
        otherDictionary.put(1, 1);
        assertNotEquals(
                "Wrong value after comparing not equals dictionary",
                dictionary, otherDictionary
        );

        otherDictionary = dictionary.copy();
        assertEquals(
                "Wrong value after comparing dictionary copy", dictionary,
                otherDictionary
        );
    }

    /**
     * Test about many items dictionary fromKeys method.
     */
    @Test
    public void testFromKeys() {
        Dictionary newDictionary = Dictionary.fromKeys(new Tuple(dictionary));
        assertEquals(
                "Wrong size from tuple keys dictionary",
                32, newDictionary.size()
        );

        for (int i = 0; i < 32; i++) {
            assertNull(
                    "Wrong value from tuple keys dictionary",
                    newDictionary.get(i)
            );
        }

        newDictionary = Dictionary.fromKeys(dictionary);
        assertEquals(
                "Wrong size from array keys dictionary",
                32, newDictionary.size()
        );

        for (int i = 0; i < 32; i++) {
            assertNull(
                    "Wrong value from array keys dictionary",
                    newDictionary.get(i)
            );
        }

        newDictionary = Dictionary.fromKeys(new Tuple(dictionary), "test");
        for (int i = 0; i < 32; i++) {
            assertEquals(
                    "Wrong value from keys dictionary",
                    "test", newDictionary.get(i)
            );
        }

        newDictionary = Dictionary.fromKeys(dictionary, "test");
        for (int i = 0; i < 32; i++) {
            assertEquals(
                    "Wrong value from keys dictionary",
                    "test", newDictionary.get(i)
            );
        }

        newDictionary = Dictionary.fromKeys(dictionary.keys());
        for (Object key : dictionary.keys()) {
            assertTrue(
                    "From keys dictionary does not have expected keys",
                    newDictionary.containsKey(key)
            );
        }

        newDictionary = Dictionary.fromKeys(
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
     * Test about many items dictionary setDefault method.
     */
    @Test
    public void setDefault() {
        Object value = dictionary.setDefault(10);
        assertEquals(
                "Wrong value",
                10, value
        );

        value = dictionary.setDefault(10, "Test");
        assertEquals(
                "Wrong value",
                10, value
        );

        value = dictionary.setDefault(32);
        assertNull("Wrong value", value);

        dictionary.pop(32);

        value = dictionary.setDefault(32, "Test");
        assertEquals(
                "Wrong value",
                "Test", value
        );
    }
}
