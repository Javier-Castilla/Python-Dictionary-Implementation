package org.ulpgc.edp.tests;

import org.junit.*;
import org.ulpgc.edp.exceptions.*;
import org.ulpgc.edp.model.Dictionary;

import static org.junit.Assert.*;

public class TestTwoItemsDictionary {
    private Dictionary dictionary;
    @Before
    public void init() throws KeyErrorException {
        this.dictionary = new Dictionary();
        dictionary.put("1", 1);
        dictionary.put("2", 2);
    }

    @Test
    public void testPutAndPop() throws EmptyDictionaryException, KeyErrorException {
        dictionary.put("3", 2);
        int len = dictionary.length();
        assertEquals(
                "La longitud del diccionario con dos elementos" +
                        "no coincide con la esperada.",
                2, len
        );

        Object value = dictionary.pop("2");
        len = dictionary.length();
        assertEquals(
                "La longitud del diccionario con dos elementos" +
                        "no coincide con la esperada al eliminar uno.",
                1, len
        );

        assertEquals(
                "El valor devuelto al eliminar una pareja" +
                        "clave - valor no coincide con el esperado.",
                2, value
        );

        value = dictionary.pop("1");
        len = dictionary.length();
        assertEquals(
                "La longitud del diccionario con in elemento" +
                        "no coincide con la esperada al eliminarlo.",
                0, len
        );

        assertEquals(
                "El valor devuelto al eliminar una pareja" +
                        "clave - valor no coincide con el esperado.",
                1, value
        );
    }

    @Test
    public void replace() throws EmptyDictionaryException, KeyErrorException {
        dictionary.put("1", 100);
        int len = dictionary.length();
        assertEquals(
                "La longitud del diccionario con un elemento" +
                        "no coincide con la esperada al actualizarlo.",
                1, len
        );

        Object value = dictionary.get("1");
        assertEquals(
                "El valor no coincide con el esperado al actualizar" +
                        "una pareja clave - valor.",
                100, value
        );
    }

    @Test
    public void testString()  {
        String str = dictionary.toString();
        assertEquals(
                "La representación como string del diccionario" +
                        "de un elemento no coincide con la esperada.",
                "{'1': 1}", str
        );
    }

    @Test
    public void testPopItem() throws EmptyDictionaryException {
        Object value = dictionary.popitem();
        assertEquals(
                "El valor de la pareja eliminada no coincide" +
                        "con la esperada.",
                1, value
        );
        int len = dictionary.length();
        assertEquals(
                "La longitud del diccionario con un elemento" +
                        "no coincide con la esperada al eliminarlo.",
                0, len
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
                        "valor no es el esperado.",
                1, value
        );
    }
}
