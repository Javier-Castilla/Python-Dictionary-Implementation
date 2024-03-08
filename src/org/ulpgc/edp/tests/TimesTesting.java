package org.ulpgc.edp.tests;

import org.ulpgc.edp.model.Dictionary;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class TimesTesting {
    public TimesTesting(int maxElements) {
        String rutaArchivo = "times.csv";
        int size = 1;
        double times = (maxElements + 2)*1e6;

        try {
            File archivo = new File(rutaArchivo);
            FileWriter escritor = new FileWriter(archivo);
            BufferedWriter bufferEscritor = new BufferedWriter(escritor);
            bufferEscritor.write("size;miliseconds;seconds");
            bufferEscritor.newLine();

            while (size < times) {
                long startTime = System.nanoTime();
                Dictionary d = new Dictionary();

                for (int i = 0; i < size; i++) {
                    d.put("TestingKey" + i, i);
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
