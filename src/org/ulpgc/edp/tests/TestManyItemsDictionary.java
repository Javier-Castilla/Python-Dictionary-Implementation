package org.ulpgc.edp.tests;

import org.junit.*;
import org.ulpgc.edp.exceptions.*;
import java.security.KeyException;
import org.ulpgc.edp.model.Dictionary;
import org.ulpgc.edp.model.Tuple;

import java.util.Arrays;
import static org.junit.Assert.*;

/**
 * Testing class for test a Dictionary with many items.
 *
 * @author Javier Castilla
 * @author David Miranda
 * @author Esteban Trujillo
 * @author Elena Artiles
 */
public class TestManyItemsDictionary {
    private Dictionary dictionary;
    private Object[] keys;
    @Before
    public void init() {
        this.dictionary = new Dictionary();
        this.keys = new Object[32];
        for (int i = 0; i < 32; i++) {
            keys[i] = i;
            dictionary.put(i, i);
        }
    }

    @Test
    public void testLen() {
        int dictionaryLength = dictionary.size();
        assertEquals(
                "Tamaño incorrecto",
                32, dictionaryLength
        );
    }

    @Test
    public void testPutAndPopitem1() {
        dictionary.put("Testing", 10);
        int dictionaryLength = dictionary.size();
        assertEquals(
                "Tamaño incorrecto",
                33, dictionaryLength
        );
    }

    @Test
    public void testPutAndPopitem2() throws EmptyDictionaryException {
        dictionary.put("Testing", 10);
        dictionary.popitem();
        int dictionaryLength = dictionary.size();
        assertEquals(
                "Tamaño incorrecto",
                32, dictionaryLength
        );
    }

    @Test
    public void testPutAndPopitem3() throws EmptyDictionaryException {
        dictionary.put("Testing", 10);
        Tuple item = dictionary.popitem();
        assertEquals(
                "El elemento devuelto no es correcto",
                new Tuple("Testing", 10), item
        );
    }

    @Test
    public void testReplace1() {
        dictionary.put(17, 100);
        int dictionaryLength = dictionary.size();
        assertEquals(
                "Tamaño incorrecto",
                32, dictionaryLength
        );
    }

    @Test
    public void testReplace2() throws KeyException {
        dictionary.put(17, 100);
        Object value = dictionary.get(17);
        assertEquals(
                "El elemento no se ha actualizado correctamente",
                100, value
        );
    }

    @Test
    public void testReplace3() throws EmptyDictionaryException {
        dictionary.put(17, 100);
        Object value = dictionary.popitem();
        assertNotEquals(
                "Valor incorrecto",
                new Object[]{31, 31}, value
        );
    }

    @Test
    public void testReplace4() throws KeyException {
        dictionary.put(17, 100);
        Object value = dictionary.pop(17);
        assertEquals(
                "Valor incorrecto",
                100, value
        );
    }

    @Test
    public void testClear1() {
        dictionary.clear();
        int dictionaryLength = dictionary.size();
        assertEquals(
                "Tamaño incorrecto",
                0, dictionaryLength
        );
    }

    @Test(expected = EmptyDictionaryException.class)
    public void testClear2() throws EmptyDictionaryException {
        dictionary.clear();
        dictionary.popitem();
    }

    @Test
    public void testEquals1() {
        Dictionary otherDictionary = new Dictionary();
        for (int i = 0; i < 32; i++) {
            otherDictionary.put(i, i);
        }
        assertTrue(
                "Valor incorrecto",
                dictionary.equals(otherDictionary)
        );
    }

    @Test
    public void testEquals2() {
        assertTrue(
                "Valor incorrecto",
                dictionary.equals(dictionary)
        );
    }

    @Test
    public void testEquals3() {
        Dictionary otherDictionary = new Dictionary();
        otherDictionary.put(1, 1);
        assertFalse(
                "Valor incorrecto",
                dictionary.equals(otherDictionary)
        );
    }

    @Test
    public void testFromKeys1() {
        Dictionary newDictionary = Dictionary.fromKeys(Arrays.asList(keys));
        assertTrue(
                "Valores incorrectos",
                dictionary.keys().equals(newDictionary.keys())
        );
    }

    @Test
    public void testFromKeys2() {
        Dictionary newDictionary = Dictionary.fromKeys(
                Arrays.asList(keys), Arrays.asList(keys)
        );
        assertTrue(
                "Valores incorrectos",
                dictionary.equals(newDictionary)
        );
    }
}
