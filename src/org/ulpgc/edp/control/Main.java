package org.ulpgc.edp.control;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.ulpgc.edp.exceptions.KeyErrorException;
import org.ulpgc.edp.model.Dictionary;
import org.ulpgc.edp.tests.*;

import java.util.Arrays;

@RunWith(Suite.class)
@SuiteClasses({
        TestEmptyDictionary.class,
        TestOneItemDictionary.class
})

public class Main {
    public static void main(String[] args) throws KeyErrorException {
        org.junit.runner.JUnitCore.main("org.ulpgc.edp.control.Main");
    }
}
