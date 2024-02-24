package org.ulpgc.edp.control;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.ulpgc.edp.exceptions.*;
import org.ulpgc.edp.model.*;
import org.ulpgc.edp.tests.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Arrays;

@RunWith(Suite.class)
@SuiteClasses({
        TestEmptyDictionary.class,
        TestOneItemDictionary.class
})

public class Main {
    public static void main(String[] args) throws KeyErrorException {
        //org.junit.runner.JUnitCore.main("org.ulpgc.edp.control.Main");

        Dictionary d = new Dictionary();

        for (int i = 0; i < 30; i++) {
            d.put("Hola", i);
        }

        System.out.println(d);

        d.clear();

        System.out.println(d);
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
