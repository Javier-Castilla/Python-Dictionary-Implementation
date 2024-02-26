package org.ulpgc.edp.tests;

import org.junit.*;
import org.ulpgc.edp.exceptions.*;
import org.ulpgc.edp.model.Dictionary;
import static org.junit.Assert.*;

public class TestOneItemDictionary {
    private Dictionary dictionary;
    @Before
    public void init() {
        this.dictionary = new Dictionary();
        dictionary.put("1", 1);
    }

    @Test
    public void testPutAndPop1() {
        dictionary.put("2", 2);
        int len = dictionary.size();
        assertEquals(
                "Tamaño incorrecto",
                2, len
        );
    }

    @Test
    public void testPutAndPop2() throws KeyErrorException {
        dictionary.put("2", 2);
        dictionary.pop("2");
        int len = dictionary.size();
        assertEquals(
                "Tamaño incorrecto",
                1, len
        );
    }

    @Test
    public void testPutAndPop3() throws EmptyDictionaryException {
        dictionary.put("2", 2);
        Object[] item = dictionary.popitem();
        assertEquals(
                "Valor incorrecto",
                new Object[]{"2", 2}, item
        );
    }

    @Test
    public void testPutAndPop4() throws KeyErrorException {
        dictionary.put("2", 2);
        Object value = dictionary.pop("2");
        assertEquals(
                "Valor incorrecto",
                2, value
        );
    }

    @Test
    public void testPutAndPop5() throws KeyErrorException {
        dictionary.put("2", 2);
        Object value = dictionary.pop("2");
        assertEquals(
                "Valor incorrecto",
                2, value
        );
    }

    @Test
    public void testReplace1() {
        dictionary.put("1", 100);
        int dictionaryLength = dictionary.size();
        assertEquals(
                "Tamaño incorrecto",
                1, dictionaryLength
        );
    }

    @Test
    public void testReplace2() throws KeyErrorException {
        dictionary.put("1", 100);
        Object value = dictionary.get("1");
        assertEquals(
                "Valor incorrecto",
                100, value
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
                5, dictionaryLength
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
                "{'1': 1, 0: 0, 1: 1, 2: 2, 3: 3}", str
        );
    }

    @Test
    public void testString()  {
        String str = dictionary.toString();
        assertEquals(
                "Representación incorrecta",
                "{'1': 1}", str
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
    public void testKeys() {
        String keys = dictionary.keys().toString();
        assertEquals(
                "Las claves devueltas no corresponden " +
                        "con las esperadas.",
                "DictionaryKeys(['1'])", keys
        );
    }

    @Test
    public void testValues() {
        String values = dictionary.values().toString();
        assertEquals(
                "Los valores devueltos no corresponden " +
                        "con las esperados.",
                "DictionaryValues([1])", values
        );
    }

    @Test
    public void testItems() {
        String items = dictionary.items().toString();
        assertEquals(
                "Las parejas devueltas no coinciden con " +
                        "las esperadas",
                "DictionaryItems([('1', 1)])", items
        );
    }

    @Test
    public void testContainsKey() {
        assertTrue(
                "Valor incorrecto",
                dictionary.containsKey("1")
        );
    }

    @Test
    public void testPopItem1() throws EmptyDictionaryException {
        Object[] values = dictionary.popitem();
        assertEquals(
                "Valor incorrecto",
                new Object[]{"1", 1}, values
        );
    }

    @Test
    public void testPopItem2() throws EmptyDictionaryException {
        dictionary.popitem();
        int dictionaryLength = dictionary.size();
        assertEquals(
                "Tamaño incorrecto",
                0, dictionaryLength
        );
    }

    @Test(expected = KeyErrorException.class)
    public void testGetNoneExistingKey() throws KeyErrorException  {
        dictionary.get("2");
    }

    @Test
    public void tesPop() throws KeyErrorException  {
        Object value = dictionary.pop("1");
        assertEquals(
                "El elemento devuelto al eliminar una pareja clave -" +
                        " valor no es el esperado.",
                1, value
        );
    }

    @Test
    public void testEquals1() {
        Dictionary otherDictionary = new Dictionary();
        otherDictionary.put("1", 1);
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
}
