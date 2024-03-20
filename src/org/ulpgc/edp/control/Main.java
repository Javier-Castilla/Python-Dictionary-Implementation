package org.ulpgc.edp.control;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.ulpgc.edp.model.dct.*;
import org.ulpgc.edp.model.tpl.*;
import org.ulpgc.edp.tests.*;

import java.util.HashMap;
import java.util.Map;

@RunWith(Suite.class)
@SuiteClasses({
        TestEmptyDictionary.class,
        TestOneItemDictionary.class,
        TestManyItemsDictionary.class
})

public class Main {
    static final int LIMIT = 16777216;
    static final Object[] objects = new Object[LIMIT];
    static final String URL = "To access this Dictionary documentation you can" +
            " visit the Documentation Website with the URL given in" +
            " doc/DocumentationWeb";
    public static void main(String[] args) throws Exception {
        System.out.printf("\u001B[1;33m%s\u001B[m\n%n", URL);

        Dictionary d = Dictionary.fromKeys(new Tuple(1, 2, 3, 4, 5, new Tuple(1, 2, 3), 8, 12, 19));

        System.out.println(d);

        // Comment the lines below to make your own tests.
        //org.junit.runner.JUnitCore.main("org.ulpgc.edp.control.Main");

        // Make tests with main methods and prints result in csv format.
        // Parameter notes millions of elements to test.
        //TimesTesting.doTestPut(2);
        //TimesTesting.doTestGet(2);
        //TimesTesting.doTestPop(2);
    }
}
