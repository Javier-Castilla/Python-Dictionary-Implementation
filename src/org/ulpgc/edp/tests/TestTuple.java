package org.ulpgc.edp.tests;

import org.junit.*;
import org.ulpgc.edp.exceptions.*;
import org.ulpgc.edp.model.tpl.Tuple;
import java.util.Iterator;
import static org.junit.Assert.*;

/**
 * Testing class for test tuple.
 *
 * @author Javier Castilla
 * @version 22-03-2024
 */
public class TestTuple {
    /**
     * Test about empty tuple size.
     */
    @Test
    public void testEmptyTuple1() {
        Tuple tuple = new Tuple();

        assertEquals(
                "Wrong size after initializing empty tuple",
                0, tuple.length()
        );
    }

    /**
     * Test about empty tuple string.
     */
    @Test
    public void testEmptyTuple2() {
        Tuple tuple = new Tuple();

        assertEquals(
                "Wrong string after initializing empty tuple",
                "()", tuple.toString()
        );
    }

    /**
     * Test about empty tuple exception thrown.
     */
    @Test (expected = IndexErrorException.class)
    public void testEmptyTuple3() throws IndexErrorException {
        Tuple tuple = new Tuple();
        tuple.get(0);
    }

    /**
     * Test about many items tuple size.
     */
    @Test
    public void testManyItemsTuple1() {
        Tuple tuple = new Tuple(1, 2, 3, 4, 5);

        assertEquals(
                "Wrong size after initializing many items tuple",
                5, tuple.length()
        );
    }

    /**
     * Test about many items tuple string.
     */
    @Test
    public void testManyItemsTuple2() {
        Tuple tuple = new Tuple(1, 2, 3, 4, 5);

        assertEquals(
                "Wrong string after initializing empty tuple",
                "(1, 2, 3, 4, 5)", tuple.toString()
        );
    }

    /**
     * Test about many items tuple values.
     */
    @Test
    public void testManyItemsTuple3() {
        Tuple tuple = new Tuple(1, 2, 3, 4, 5);

        for (int i = 0; i < 5; i++) {
            assertEquals(
                    "Wrong value from many items tuple",
                    i + 1, tuple.get(i)
            );
        }
    }

    /**
     * Test about many items tuple with inner tuples size.
     */
    @Test
    public void testInnerTuple1() {
        Tuple tuple = new Tuple(
                new Tuple(1, 2), new Tuple(3, 4)
        );

        assertEquals(
                "Wrong size after initializing many items tuple",
                2, tuple.length()
        );
    }

    /**
     * Test about many items tuple with inner tuples string.
     */
    @Test
    public void testInnerTuple2() {
        Tuple tuple = new Tuple(
                new Tuple(1, 2), new Tuple(3, 4)
        );

        assertEquals(
                "Wrong string after initializing empty tuple",
                "((1, 2), (3, 4))", tuple.toString()
        );
    }

    /**
     * Test about many items tuple with inner tuples values.
     */
    @Test
    public void testInnerTuple3() {
        Tuple tuple = new Tuple(
                new Tuple(1, 2), new Tuple(3, 4)
        );

        assertEquals(
                "Wrong value from many items tuple",
                new Tuple(1, 2), tuple.get(0)
        );

        assertEquals(
                "Wrong value from many items tuple",
                new Tuple(3, 4), tuple.get(1)
        );
    }

    /**
     * Test about many items tuple from iterable. Checks size, string, values
     * and iterability.
     */
    @Test
    public void testTupleFromIter() {
        Tuple tuple = new Tuple(() -> new Iterator<>() {
            private int index = 0;

            @Override
            public boolean hasNext() {
                return index < 5;
            }

            @Override
            public Tuple next() {
                if (hasNext()) {
                    index++;
                    return new Tuple(1);
                }
                throw new java.util.NoSuchElementException();
            }
        });

        assertEquals(
                "Wrong size after initializing tuple from iterable",
                5, tuple.length()
        );

        assertEquals(
                "Wrong string after initializing tuple from iterable",
                "((1), (1), (1), (1), (1))", tuple.toString()
        );

        for (Object item : tuple) {
            assertEquals(
                    "Wrong value after initializing tuple from iterable",
                    new Tuple(1), item
            );
        }
    }
}
