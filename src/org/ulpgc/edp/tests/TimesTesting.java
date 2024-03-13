package org.ulpgc.edp.tests;

import org.ulpgc.edp.model.Dictionary;

public class TimesTesting {

    /**
     * Static method used to test dicctionary insert method rate.
     */
    public static void doTestPut(int maxMElements) {
        int size = 1;
        double times = (maxMElements + 2)*1e6;

        System.out.println("size;miliseconds;seconds");

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
    }

    /**
     * Static method used to test dicctionary get method rate.
     */
    public static void doTestGet(int maxMElements) {
        int size = 1;
        double times = (maxMElements + 2)*1e6;

        System.out.println("size;miliseconds;seconds");

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
    }

    /**
     * Static method used to test dicctionary pop method rate.
     */
    public static void doTestPop(int maxMElements) {
        int size = 1;
        double times = (maxMElements + 2)*1e6;

        System.out.println("size;miliseconds;seconds");

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
    }
}
