package org.ulpgc.edp.tests;

import org.junit.*;
import org.junit.runner.JUnitCore;
import org.ulpgc.edp.exceptions.KeyErrorException;
import org.ulpgc.edp.model.Dictionary;

import static org.junit.Assert.*;

public class TestEmptyDictionary {
    private Dictionary dictionary;

    @Before
    public void init() {
        this.dictionary = new Dictionary();
    }

    @Test
    public void testLen1() {
        int dictionaryLen = dictionary.length();
        assertEquals(
                "El tamaño debe ser 0 al inicializar el diccionario sin un tamaño determinado",
                0, dictionaryLen
        );
    }

    @Test
    public void testLen2() {
        dictionary.put("1", 1);
        dictionary.put("2", 2);
        int dictionaryLen = dictionary.length();
        assertEquals(
                "El tamaño debe ser 2 al insertar dos nuevas parejas clave - valor en un diccionario vacío",
                2, dictionaryLen
        );
    }

    @Test
    public void testLen3() {
        dictionary.put("1", 1);
        dictionary.put("2", 2);
        dictionary.put("3", 3);
        int dictionaryLen = dictionary.length();
        assertEquals(
                "El tamaño debe ser 3 al insertar tres nuevas parejas clave - valor en un diccionario vacío",
                3, dictionaryLen
        );
    }

    @Test
    public void testPutAndPop1() {
        dictionary.put("1", 1);
        int dictionaryLen = dictionary.length();
        assertEquals(
                "El tamaño debe ser 1 al insertar una nueva pareja clave - valor en un diccionario vacío",
                1, dictionaryLen
        );
        Object value = dictionary.pop("1");
        dictionaryLen = dictionary.length();
        assertEquals(
                "El tamaño debe ser 0 al eliminar una pareja clave - valor en un diccionario de un elemento",
                1, dictionaryLen
        );
        assertEquals(
                "El elemento eliminado debería ser 1 para un diccionario con pareja clave - valor {'1': 1}",
                1, value);
    }

    @Test
    public void testPutAndPop2() {
        dictionary.put("1", 1);
        int dictionaryLen = dictionary.length();
        assertEquals(
                "El tamaño debe ser 1 al insertar una nueva pareja clave - valor en un diccionario vacío",
                1, dictionaryLen
        );
        dictionary.put("2", 2);
        dictionaryLen = dictionary.length();
        assertEquals(
                "El tamaño debe ser 2 al insertar una nueva pareja clave - valor en un diccionario de un elemento",
                2, dictionaryLen
        );
        Object value1 = dictionary.pop("1");
        Object value2 = dictionary.pop("2");
        dictionaryLen = dictionary.length();
        assertEquals(
                "El tamaño debe ser 0 al eliminar una pareja clave - valor en un diccionario de un elemento",
                1, dictionaryLen
        );
        assertEquals(
                "El elemento eliminado debería ser 1 para un diccionario con parejas clave - valor {'1': 1, '2': 2}",
                1, value1
        );
        assertEquals(
                "El elemento eliminado debería ser 2 para un diccionario con parejas clave - valor {'2': 2}",
                2, value2
        );
    }

    @Test(expected = KeyErrorException.class)
    public void testGetNoneExistingKey() {
        dictionary.put("1", 1);
        dictionary.get("1");
    }

    @Test(expected = EmptyDictionaryException.class)
    public void tesEmptyPop() {
        dictionary.pop("1");
    }

    @Test(expected = EmptyDictionaryException.class)
    public void testEmptyGet() {
        dictionary.get("1");
    }

    @Test(expected = EmptyDictionaryException.class)
    public void testEmptyPopItem() {
        dictionary.popitem();
    }
}
