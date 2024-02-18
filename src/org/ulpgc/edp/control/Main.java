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
        Dictionary d = new Dictionary();


        for (int i = 0; i < 1000000; i++) {
            d.put("Hola" + Integer.toString(i), i);
        }

        System.out.println();
        for (LinkedList list : d.entries()) {
            System.out.println(list);
        }

        System.out.println(d);
    }
}
