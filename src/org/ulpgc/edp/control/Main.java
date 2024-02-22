package org.ulpgc.edp.control;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.ulpgc.edp.exceptions.KeyErrorException;
import org.ulpgc.edp.model.Dictionary;
import org.ulpgc.edp.model.LinkedList;
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
        long startTime = System.nanoTime();

        // Aquí va tu código
        Dictionary d = new Dictionary();


        for (int i = 0; i < 1000; i++) {
            d.put("Hola" + Integer.toString(i), i);
        }

        //System.out.println(d);

        long endTime = System.nanoTime();
        // Calcula la duración de la ejecución en segundos
        double durationInSeconds = (endTime - startTime) / 1e9;

        System.out.println("Tiempo de ejecución: " + durationInSeconds + " segundos");
    }
}
