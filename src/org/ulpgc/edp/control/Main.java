package org.ulpgc.edp.control;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.ulpgc.edp.model.*;
import org.ulpgc.edp.tests.*;

import java.util.ArrayList;

@RunWith(Suite.class)
@SuiteClasses({
        TestEmptyDictionary.class,
        TestOneItemDictionary.class,
        TestManyItemsDictionary.class
})

public class Main {
    static final int LIMIT = 16777216 / 16;
    static final Object[] objects = new Object[LIMIT];
    static final String URL = "To access this Dictionary documentation you can" +
            " visit the Documentation Website with the URL given in" +
            " doc/DocumentationWeb";
    public static void main(String[] args) throws Exception {
        System.out.println(String.format("\033[1;33m%s\033[m\n", URL));

        // Comment the line below to make your own tests.
        //org.junit.runner.JUnitCore.main("org.ulpgc.edp.control.Main");

        // Make tests inserting power of two elements into the Dictionary.
        //TimesTesting.doTestPop(2);

        ArrayList<Object> l = new ArrayList<>();
        for (int i = 0; i <= 9; i++) {
            l.add(i);
        }
        Tuple t = new Tuple(new Object[]{1, 2, "3", 4, 5, 6, 7, 8, 9});
        System.out.println(t);

        /*
        long startTime = System.nanoTime();
        Dictionary d = new Dictionary();

        for (int i = 0; i < LIMIT; i++) {
            d.put("TestingKey" + i, i);
        }

        long endTime = System.nanoTime();
        long duration = (endTime - startTime);
        double ms = duration / 1e6;
        double sc = ms / 1000;

        System.out.println(
                "Tiempo de ejecución para " + LIMIT + " elementos: "+
                        ms + "ms (" + sc + " segs)"
        );
        */
    }
}
