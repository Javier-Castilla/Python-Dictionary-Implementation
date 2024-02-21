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
        long executionTime = (endTime - startTime) / 1000000; // Convertir a milisegundos
        System.out.println("Tiempo de ejecución: " + executionTime + " milisegundos");
        System.out.println("Elementos introducidos: " + d.length());
        System.out.println(d.get("Hola998"));
    }
}
