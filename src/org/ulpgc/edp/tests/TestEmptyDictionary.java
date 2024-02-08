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
        Dictionary dictionary = new Dictionary();
    }

    @Test
    public void testLen() {
        int dictionaryLen = dictionary.length();
        assertEquals(
                "El tamaño debe ser 0 al inicializar el diccionario sin un tamaño determinado",
                0, dictionaryLen
        );
    }

    @Test
    public void testPut() {
        dictionary.put("1", 1);
        int dictionaryLen = dictionary.length();
        assertEquals(
                "El tamaño debe ser 1 al insertar una nueva pareja clave - valor en un diccionario vacío",
                1, dictionaryLen
        );
    }

    @Test
    public void testPop() {
        Object value = dictionary.pop("1");
        int dictionaryLen = dictionary.length();
        assertEquals(
                "El tamaño debe ser 1 al insertar una nueva pareja clave - valor en un diccionario vacío",
                1, dictionaryLen
        );
        assertEquals(
                "El elemento eliminado debería ser 1 para un diccionario con pareja clave - valor {'1': 1}",
                1, value);
    }

    @Test(expected = KeyErrorException.class)
    public void testGet() {
        dictionary.get("1");
    }
}
