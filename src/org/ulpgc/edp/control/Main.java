package org.ulpgc.edp.control;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.ulpgc.edp.doc.Documentation;
import org.ulpgc.edp.model.*;
import org.ulpgc.edp.tests.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

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
            " access to the Documentation Web with the URL given in" +
            " doc/DocumentationWeb";
    public static void main(String[] args) throws Exception {
        System.out.println(String.format("\033[1;33m%s\033[m\n", URL));

        // Comment the line below to make your own tests.
        //timesFile();
        //org.junit.runner.JUnitCore.main("org.ulpgc.edp.control.Main");

        //Documentation.open(false);

        //long startTime = System.nanoTime();
        new TimesTesting(16);
        //Dictionary d = new Dictionary();

        for (int i = 0; i < objects.length; i++) {
            objects[i] = "TestingKey" + i;
        }

        long startTime = System.nanoTime();
        Dictionary d = new Dictionary();

        for (int i = 0; i < objects.length; i++) {
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
    }
}
