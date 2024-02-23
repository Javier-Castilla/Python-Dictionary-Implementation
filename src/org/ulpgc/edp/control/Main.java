package org.ulpgc.edp.control;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.ulpgc.edp.exceptions.KeyErrorException;
import org.ulpgc.edp.model.*;
import org.ulpgc.edp.tests.*;

import java.util.Arrays;

@RunWith(Suite.class)
@SuiteClasses({
        TestEmptyDictionary.class,
        TestOneItemDictionary.class
})

public class Main {
    public static void main(String[] args) throws KeyErrorException {
        //org.junit.runner.JUnitCore.main("org.ulpgc.edp.control.Main");
        long startTime = System.nanoTime(); // Obtener el tiempo de inicio en nanosegundos
        Dictionary d = new Dictionary();

        for (int i = 0; i < 10000000; i++) {
            d.otherPut("Hola" + Integer.toString(i), i);
        }
        long endTime = System.nanoTime(); // Obtener el tiempo de finalización en nanosegundos

        // Calcular la diferencia de tiempo
        long duration = (endTime - startTime);

        // Convertir la duración de nanosegundos a segundos (opcional)
        double seconds = duration / 1e9;

        // Imprimir el tiempo de ejecución
        System.out.println("Tiempo de ejecución: " + duration + " nanosegundos");
        System.out.println("Tiempo de ejecución: " + seconds + " segundos");
    }
}
