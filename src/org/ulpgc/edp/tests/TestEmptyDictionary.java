package org.ulpgc.edp.tests;

import org.junit.*;
import org.ulpgc.edp.exceptions.*;
import org.ulpgc.edp.model.Dictionary;
import static org.junit.Assert.*;

/**
 * Testing class for test an empty Dictionary.
 *
 * @author Javier Castilla
 * @author David Miranda
 * @author Esteban Trujillo
 * @author Elena Artiles
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
    public void testPutAndPop1() {
        dictionary.put("1", 1);
        int dictionaryLen = dictionary.size();
        assertEquals(
                "El tamaño debe ser 1 al insertar una nueva pareja " +
                        "clave - valor en un diccionario vacío",
                1, dictionaryLen
        );
    }

    @Test
    public void testPutAndPop2() throws KeyErrorException  {
        dictionary.put("1", 1);
        dictionary.pop("1");
        int dictionaryLength = dictionary.size();
        assertEquals(
                "El tamaño debe ser 0 al eliminar una pareja clave " +
                        "- valor en un diccionario de un elemento",
                0, dictionaryLength
        );
    }

    @Test
    public void testPutAndPop3() throws KeyErrorException  {
        dictionary.put("1", 1);
        Object value = dictionary.pop("1");
        assertEquals(
                "El elemento quitado es incorrecto",
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
                "Tamaño incorrecto",
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
                "Los elementos son incorrectos",
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
        Object[] value = dictionary.popitem();
        assertEquals(
                "El último elemento no es correcto",
                new Object[]{3, 3}, value
        );
    }

    @Test
    public void testCopy1() {
        Dictionary dictionaryCopy = dictionary.copy();
        assertTrue(
                "Valor incorrecto",
                dictionary.equals(dictionaryCopy)
        );
    }

    @Test
    public void testCopy2() {
        Dictionary dictionaryCopy = dictionary.copy();
        int dictionaryCopyLength = dictionaryCopy.size();
        assertEquals(
                "Tamaño incorrecto",
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
    public void testSetDefault3() throws KeyErrorException {
        dictionary.setDefault("Test");
        Object value = dictionary.get("Test");
        assertEquals(
                "Valor incorrecto",
                null, value
        );
    }

    @Test
    public void testSetDefault4() throws KeyErrorException {
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

    @Test(expected = KeyErrorException.class)
    public void testGetNoneExistingKey() throws KeyErrorException  {
        dictionary.get("1");
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
