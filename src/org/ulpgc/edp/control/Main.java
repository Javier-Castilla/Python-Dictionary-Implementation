package org.ulpgc.edp.control;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.ulpgc.edp.model.dct.*;
import org.ulpgc.edp.model.tpl.*;
import org.ulpgc.edp.tests.*;

import java.util.Arrays;
import java.util.Iterator;

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
        //org.junit.runner.JUnitCore.main("org.ulpgc.edp.control.Main");

        Object[] dnis = new Object[10];
        for (int i = 0; i < 10;) {
            dnis[i++] = 45454545 + i;
            dnis[i++] = "Javier" + i;
        }

        Object[] subjects = new Object[8];
        for (int i = 0; i < 8;) {
            subjects[i++] = 45454545 + i;
            subjects[i++] = "Subject" + i;
        }


        
        Dictionary d1 = new Dictionary("dnis", new Dictionary(dnis));

        Object[] students = new Object[8];
        for (int i = 0; i < 8;) {
            students[i++] = "Subject" + i;
            students[i++] = d1;
        }

        Dictionary d2 = new Dictionary("subjects", new Dictionary(subjects), "students", new Dictionary(students));

        System.out.println(d2);

        Dictionary dir = (Dictionary) d2.get("students");

        for (Object k : dir.keys()) {
            System.out.println(dir.get(k));
        }

        System.out.println();

        // Make tests with main methods and prints result in csv format.
        // Parameter notes millions of elements to test.
        //TimesTesting.doTestPut(2);
        //TimesTesting.doTestGet(2);
        //TimesTesting.doTestPop(2);
    }
}
