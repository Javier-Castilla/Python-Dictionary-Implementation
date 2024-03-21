package org.ulpgc.edp.control;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.ulpgc.edp.model.dct.*;
import org.ulpgc.edp.model.tpl.*;
import org.ulpgc.edp.tests.*;

@RunWith(Suite.class)
@SuiteClasses({
        TestEmptyDictionary.class,
        TestOneItemDictionary.class,
        TestManyItemsDictionary.class
})

public class Main {
    static final int LIMIT = 16777216;
    static final String URL = "To access this Dictionary documentation you can" +
            " visit the Documentation Website with the URL given in" +
            " doc/DocumentationWeb";
    public static void main(String[] args) {
        System.out.printf("\u001B[1;33m%s\u001B[m\n%n", URL);

        // Comment the lines below to make your own tests.
        org.junit.runner.JUnitCore.main("org.ulpgc.edp.control.Main");

        Dictionary d = new Dictionary(1, 2, 3, 4, 5, 6, 7, 8);

        // Make tests with main methods and prints result in csv format.
        // Parameter notes millions of elements to test.
        //TimesTesting.doTestPut(2);
        //TimesTesting.doTestGet(2);
        //TimesTesting.doTestPop(2);
    }
}
