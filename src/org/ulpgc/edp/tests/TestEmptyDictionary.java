package org.ulpgc.edp.tests;

import org.junit.*;
import org.junit.runner.JUnitCore;
import org.ulpgc.edp.exceptions.*;
import org.ulpgc.edp.model.*;

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
    public void testLen2() throws KeyErrorException {
        dictionary.put("1", 1);
        dictionary.put("2", 2);
        int dictionaryLen = dictionary.length();
        assertEquals(
                "El tamaño debe ser 2 al insertar dos nuevas parejas clave - valor en un diccionario vacío",
                2, dictionaryLen
        );
    }

    @Test
    public void testLen3() throws KeyErrorException {
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
    public void testPutAndPop1() throws EmptyDictionaryException, KeyErrorException {
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
                0, dictionaryLen
        );
        assertEquals(
                "El elemento eliminado debería ser 1 para un diccionario con pareja clave - valor {'1': 1}",
                1, value);
    }

    @Test
    public void testPutAndPop2() throws EmptyDictionaryException, KeyErrorException {
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
                0, dictionaryLen
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

    @Test
    public void replace1() throws EmptyDictionaryException, KeyErrorException {
        dictionary.put("1", 1);
        dictionary.put("1", 100);
        Object item = dictionary.get("1");
        assertEquals(
                "El elemento devuelto al actualizar ('1': 1) no coincide con el esperado",
                100, item
        );
    }

    @Test
    public void replace2() throws EmptyDictionaryException, KeyErrorException {
        dictionary.put("1", 1);
        dictionary.put("2", 2);
        dictionary.put("3", 3);
        dictionary.put("1", 100);
        Object item = dictionary.get("1");
        assertEquals(
                "El elemento devuelto al actualizar ('1': 1) no coincide con el esperado",
                100, item
        );
        item = dictionary.get("2");
        assertEquals(
                "El elemento devuelto al obtener ('2': 2) después de actualizar ('1': 1) no coincide con el esperado",
                2, item
        );
        dictionary.put("2", 200);
        item = dictionary.get("2");
        assertEquals(
                "El elemento devuelto al actualizar ('2': 2) no coincide con el esperado",
                200, item
        );
        item = dictionary.get("3");
        assertEquals(
                "El elemento devuelto al obtener ('3': 3) después de actualizar ('1': 1) y ('2': 2) no coincide con el esperado",
                3, item
        );
        dictionary.put("3", 300);
        item = dictionary.get("3");
        assertEquals(
                "El elemento devuelto al actualizar ('3': 3) no coincide con el esperado",
                300, item
        );
    }

    @Test
    public void testString1() throws EmptyDictionaryException, KeyErrorException {
        dictionary.put("1", 1);
        String str = dictionary.toString();
        assertEquals(
                "La representación como string del diccionario no coincide con la esperada al insertar un elemento",
                "{'1': 1}", str
        );
    }

    @Test
    public void testString2() throws KeyErrorException {
        dictionary.put("1", 1);
        dictionary.put("2", 2);
        String str = dictionary.toString();
        assertEquals(
                "La representación como string del diccionario no coincide con la esperada al isnertar dos elementos",
                "{'1': 1, '2': 2}", str
        );
    }

    @Test
    public void testString3() throws KeyErrorException {
        dictionary.put(1, 1);
        String str = dictionary.toString();
        assertEquals(
                "La representación como string del diccionario no coincide con la esperada al insertar un elemento",
                "{1: 1}", str
        );
    }

    @Test
    public void testString4() throws KeyErrorException {
        dictionary.put(1, 1);
        dictionary.put(2, 2);
        String str = dictionary.toString();
        assertEquals(
                "La representación como string del diccionario no coincide con la esperada al isnertar dos elementos",
                "{1: 1, 2: 2}", str
        );
    }

    @Test
    public void testString5() throws KeyErrorException {
        dictionary.put("1", 1);
        dictionary.put("1", 100);
        String str = dictionary.toString();
        assertEquals(
                "La representación como string del diccionario no coincide con la esperada al isnertar dos elementos",
                "{'1': 100}", str
        );
    }

    @Test
    public void testString6() throws KeyErrorException {
        dictionary.put("1", 1);
        dictionary.put("2", 2);
        dictionary.put("1", 100);
        String str = dictionary.toString();
        assertEquals(
                "La representación como string del diccionario no coincide con la esperada al isnertar dos elementos",
                "{'1': 100, '2': 2}", str
        );
    }

    @Test(expected = KeyErrorException.class)
    public void testGetNoneExistingKey() throws EmptyDictionaryException, KeyErrorException {
        dictionary.put("1", 1);
        dictionary.get("2");
    }

    @Test(expected = KeyErrorException.class)
    public void testUnhasheableKey() throws KeyErrorException {
        dictionary.put(new String[3], 1);
    }

    @Test(expected = EmptyDictionaryException.class)
    public void tesEmptyPop() throws EmptyDictionaryException, KeyErrorException {
        dictionary.pop("1");
    }

    @Test(expected = EmptyDictionaryException.class)
    public void testEmptyGet() throws EmptyDictionaryException, KeyErrorException {
        dictionary.get("1");
    }

    @Test(expected = EmptyDictionaryException.class)
    public void testEmptyPopItem() throws EmptyDictionaryException, KeyErrorException {
        dictionary.popitem();
    }
}
