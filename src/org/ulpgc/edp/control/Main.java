package org.ulpgc.edp.control;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.ulpgc.edp.doc.Documentation;
import org.ulpgc.edp.exceptions.*;
import org.ulpgc.edp.model.*;
import org.ulpgc.edp.tests.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

@RunWith(Suite.class)
@SuiteClasses({
        TestEmptyDictionary.class,
        TestOneItemDictionary.class,
        TestManyItemsDictionary.class
})

public class Main {
    static final int LIMIT = 1048576;
    static final String URL = "To access this Dictionary documentation you can" +
            " acces to the Documentation Web with the URL given in" +
            " doc/DocumentationWeb";
    public static void main(String[] args) {
        System.out.println(String.format("\033[1;33m%s\033[m\n", URL));

        // Comment the line below to make your own tests.
        org.junit.runner.JUnitCore.main("org.ulpgc.edp.control.Main");

        Documentation.open(true);

        long startTime = System.nanoTime();
        Dictionary d = new Dictionary();

        for (int i = 0; i < LIMIT; i++) {
            d.put("TestingKEY" + i, i);
        }

        System.out.println(d.size());

        Object[] a1 = new Object[]{1, 2};
        Object[] a2 = new Object[]{2, 3};
        System.out.println(Dictionary.fromKeys(Arrays.asList(a1), Arrays.asList(a2)));

        long endTime = System.nanoTime();
        long duration = (endTime - startTime);
        double ms = duration / 1e6;
        double sc = ms / 1000;

        System.out.println("Tiempo de ejecución para " + LIMIT + " elementos: " + ms + "ms (" + sc + " segs)");
    }

    public static void timesFile() {
        String rutaArchivo = "times.txt";
        int size = 1;

        try {
            File archivo = new File(rutaArchivo);
            FileWriter escritor = new FileWriter(archivo);
            BufferedWriter bufferEscritor = new BufferedWriter(escritor);
            bufferEscritor.write("size;miliseconds;seconds");
            bufferEscritor.newLine();

            while (size < 32000000) {
                long startTime = System.nanoTime();
                Dictionary d = new Dictionary();

                for (int i = 0; i < size; i++) {
                    d.put("Testing" + i, i);
                }

                long endTime = System.nanoTime();
                long duration = (endTime - startTime);
                double ms = duration / 1e6;
                double sc = ms / 1000;

                bufferEscritor.write(String.format("%d;%.3f;%.3f", size, ms, sc));
                bufferEscritor.newLine();
                size <<= 1;
            }

            bufferEscritor.close();
            System.out.println("Se ha creado el archivo correctamente.");

        } catch (IOException e) {
            System.out.println("Error al crear el archivo: " + e.getMessage());
        }
    }
}
