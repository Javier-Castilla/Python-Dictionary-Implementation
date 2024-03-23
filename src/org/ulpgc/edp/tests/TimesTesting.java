package org.ulpgc.edp.tests;

import org.ulpgc.edp.model.dct.Dictionary;

/**
 * Class used to test several cases of the developed dictionary main methods.
 * The class creates a file with the time of execution.
 *
 * @author Javier Castilla
 * @version 22-03-2024
 */
public class TimesTesting {
    private static final String SEPARATOR = "\n=============================================\n";
    private static final String HEADER = "\nsize;milliseconds;seconds";

    /**
     * Static method used to test dictionary insert method rate.
     */
    public static void doTestPut(int maxMElements) {
        int size = 1;
        double times = (maxMElements + 2)*1e6;

        System.out.println("PUT" + HEADER);

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

            System.out.println(String.format("%d;%.3f;%.3f", size, ms, sc));
            size <<= 1;
        }
        System.out.println(SEPARATOR);
    }

    /**
     * Static method used to test dictionary get method rate.
     */
    public static void doTestGet(int maxMElements) {
        int size = 1;
        double times = (maxMElements + 2)*1e6;

        System.out.println("GET" + HEADER);

        while (size < times) {
            Dictionary d = new Dictionary();

            for (int i = 0; i < size; i++) {
                d.put("TestingKey" + i, i);
            }

            long startTime = System.nanoTime();
            for (int i = 0; i < size; i++) {
                d.get("TestingKey" + i);
            }

            long endTime = System.nanoTime();
            long duration = (endTime - startTime);
            double ms = duration / 1e6;
            double sc = ms / 1000;

            System.out.println(String.format("%d;%.3f;%.3f", size, ms, sc));
            size <<= 1;
        }
        System.out.println(SEPARATOR);
    }

    /**
     * Static method used to test dictionary pop method rate.
     */
    public static void doTestPop(int maxMElements) {
        int size = 1;
        double times = (maxMElements + 2)*1e6;

        System.out.println("POP" + HEADER);

        while (size < times) {
            Dictionary d = new Dictionary();

            for (int i = 0; i < size; i++) {
                d.put("TestingKey" + i, i);
            }

            long startTime = System.nanoTime();
            for (int i = 0; i < size; i++) {
                d.pop("TestingKey" + i, null);
            }

            long endTime = System.nanoTime();
            long duration = (endTime - startTime);
            double ms = duration / 1e6;
            double sc = ms / 1000;

            System.out.println(String.format("%d;%.3f;%.3f", size, ms, sc));
            size <<= 1;
        }
        System.out.println(SEPARATOR);
    }
}
