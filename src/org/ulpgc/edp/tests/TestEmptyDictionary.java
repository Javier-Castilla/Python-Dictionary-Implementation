package org.ulpgc.edp.tests;

import org.junit.*;
import org.ulpgc.edp.exceptions.*;
import org.ulpgc.edp.model.Dictionary;

import java.util.Iterator;

import static org.junit.Assert.*;

public class TestEmptyDictionary {
    private Dictionary dictionary;

    @Before
    public void init() {
        this.dictionary = new Dictionary();
    }

    @Test
    public void testLen() throws KeyErrorException  {
        int dictionaryLen = dictionary.length();
        assertEquals(
                "El tamaño debe ser 0 al inicializar el diccionario " +
                        "sin un tamaño determinado",
                0, dictionaryLen
        );
    }

    @Test
    public void testPutAndPop() throws EmptyDictionaryException, KeyErrorException  {
        dictionary.put("1", 1);
        int dictionaryLen = dictionary.length();
        assertEquals(
                "El tamaño debe ser 1 al insertar una nueva pareja " +
                        "clave - valor en un diccionario vacío",
                1, dictionaryLen
        );
        Object value = dictionary.pop("1");
        dictionaryLen = dictionary.length();
        assertEquals(
                "El tamaño debe ser 0 al eliminar una pareja clave " +
                        "- valor en un diccionario de un elemento",
                0, dictionaryLen
        );
    }

    @Test
    public void testString()  {
        String str = dictionary.toString();
        assertEquals(
                "La representación como string del diccionario " +
                        "vacío no coincide con la esperada",
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
                "Se esperaba false para comprobar si se contiene " +
                        "una clave en un diccionario vacío.",
                dictionary.containsKey("key")
        );
    }

    @Test(expected = KeyErrorException.class)
    public void testGetNoneExistingKey() throws KeyErrorException  {
        dictionary.get("1");
    }

    @Test(expected = KeyErrorException.class)
    public void testUnhasheableKey() throws KeyErrorException {
        dictionary.put(new String[3], 1);
    }

    @Test(expected = KeyErrorException.class)
    public void tesPop() throws KeyErrorException  {
        dictionary.pop("1");
    }

    @Test(expected = EmptyDictionaryException.class)
    public void testPopItem() throws EmptyDictionaryException {
        dictionary.popitem();
    }
}
