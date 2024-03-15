package org.ulpgc.edp.tests;

import org.junit.*;
import org.ulpgc.edp.exceptions.*;
import java.security.KeyException;

import org.ulpgc.edp.model.dictionaryobject.Dictionary;
import org.ulpgc.edp.model.tupleobject.Tuple;

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
                "El tamaño debe ser 0 al inicializar el diccionario " +
                        "sin un tamaño determinado",
                0, dictionaryLen
        );
    }

    @Test
    public void testPut1() {
        dictionary.put("1", 1);
        int dictionaryLen = dictionary.size();
        assertEquals(
                "Tamaño incorrecto al insertar una pareja en" +
                        " diccionario vacío",
                1, dictionaryLen
        );
    }

    @Test
    public void testPut2() throws KeyException {
        dictionary.put("1", 1);
        dictionary.pop("1");
        int dictionaryLength = dictionary.size();
        assertEquals(
                "Al introducir una pareja en un diccionario vacío" +
                        " y quitarlo después, no se obtiene el tamaño esperado",
                0, dictionaryLength
        );
    }

    @Test
    public void testPut3() throws KeyException {
        dictionary.put("1", 1);
        Object value = dictionary.pop("1");
        assertEquals(
                "Tras introducir una pareja en un diccionario vacío" +
                        " y quitarla después, no coincide con el valor esperado",
                1, value
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
                "Tamaño incorrecto al actualizar un diccionario vacío",
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
                "Los elementos son incorrectos al actualizar un" +
                        " diccionario vacío",
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
        Tuple value = dictionary.popitem();
        assertEquals(
                "El último elemento no es correcto al actualizar" +
                        " un diccionario vacío",
                new Tuple(3, 3), value
        );
    }

    @Test
    public void testCopy1() {
        Dictionary dictionaryCopy = dictionary.copy();
        assertTrue(
                "Valor incorrecto al copiar un diccionario vacío",
                dictionary.equals(dictionaryCopy)
        );
    }

    @Test
    public void testCopy2() {
        Dictionary dictionaryCopy = dictionary.copy();
        int dictionaryCopyLength = dictionaryCopy.size();
        assertEquals(
                "Tamaño incorrecto al copiar un diccionario vacío",
                0, dictionaryCopyLength

        );
    }

    @Test
    public void testSetDefault1() {
        Object value = dictionary.setDefault("Test");
        assertEquals(
                "Valor incorrecto",
                null, value
        );
    }

    @Test
    public void testSetDefault2() {
        Object value = dictionary.setDefault("Test", 10);
        assertEquals(
                "Valor incorrecto",
                10, value
        );
    }

    @Test
    public void testSetDefault3() throws KeyException {
        dictionary.setDefault("Test");
        Object value = dictionary.get("Test");
        assertEquals(
                "Valor incorrecto",
                null, value
        );
    }

    @Test
    public void testSetDefault4() throws KeyException {
        dictionary.setDefault("Test", 10);
        Object value = dictionary.get("Test");
        assertEquals(
                "Valor incorrecto",
                10, value
        );
    }

    @Test
    public void testString()  {
        String str = dictionary.toString();
        assertEquals(
                "Representación incorrecta",
                "{}", str
        );
    }

    @Test
    public void testKeys() {
        String keys = dictionary.keys().toString();
        assertEquals(
                "Las claves devueltas no corresponden " +
                        "con las esperadas.",
                "DictionaryKeys([])", keys
        );
    }

    @Test
    public void testValues() {
        String values = dictionary.values().toString();
        assertEquals(
                "Los valores devueltos no corresponden " +
                        "con las esperados.",
                "DictionaryValues([])", values
        );
    }

    @Test
    public void testItems() {
        String items = dictionary.items().toString();
        assertEquals(
                "Las parejas devueltas no coinciden con " +
                        "las esperadas",
                "DictionaryItems([])", items
        );
    }

    @Test
    public void testContainsKey() {
        assertFalse(
                "Valor incorrecto",
                dictionary.containsKey("key")
        );
    }

    @Test
    public void testEquals1() {
        Dictionary otherDictionary = new Dictionary();
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

    @Test(expected = KeyException.class)
    public void testGetNoneExistingKey() throws KeyException {
        dictionary.getItem("1");
    }

    @Test(expected = KeyException.class)
    public void tesPop() throws KeyException {
        dictionary.pop("1");
    }

    @Test(expected = EmptyDictionaryException.class)
    public void testPopItem() throws EmptyDictionaryException {
        dictionary.popitem();
    }
}
