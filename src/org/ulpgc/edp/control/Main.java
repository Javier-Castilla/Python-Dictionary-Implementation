package org.ulpgc.edp.control;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.ulpgc.edp.tests.*;

@RunWith(Suite.class)
@SuiteClasses({
        TestEmptyDictionary.class
})

public class Main {
    public static void main(String[] args) {
        org.junit.runner.JUnitCore.main("Main");
    }
}
