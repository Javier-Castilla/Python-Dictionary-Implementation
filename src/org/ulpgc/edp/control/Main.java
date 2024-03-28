package org.ulpgc.edp.control;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.ulpgc.edp.model.dct.*;
import org.ulpgc.edp.model.tpl.*;
import org.ulpgc.edp.tests.*;

@RunWith(Suite.class)
@SuiteClasses({
        TestEmptyDictionary.class,
        TestOneItemDictionary.class,
        TestManyItemsDictionary.class,
        TestDifferentItemsDictionary.class,
        TestDictionaryConstructors.class,
        TestTuple.class
})

public class Main {
    public static final int LIMIT = 16777216;
    public static final String SEPARATOR = "\n=============================================\n";
    static final String URL = "To access this Dictionary documentation you can" +
            " visit the Documentation Website with next URL:" +
            " https://javier-castilla.github.io/Java-own-Python-dictionary-implementation-DOCUMENTATION/";
    public static void main(String[] args) {
        System.out.printf("\u001B[1;33m%s\u001B[m\n%n", URL);

        // Comment the lines below to make your own tests.
        //org.junit.runner.JUnitCore.main("org.ulpgc.edp.control.Main");

        // =====================================================================

        // Real example.
        // Get passed grades from specific subject and the students names.
        Dictionary subjects = new Dictionary(
                new Tuple("40953",
                new Tuple("Fundamentos de Programación 1", 1, 6)),
                new Tuple("12345",
                new Tuple("Matemáticas", 2, 9))
        );
        Dictionary students = new Dictionary(
                new Tuple("40444444X", "Pepíto Grillo"),
                new Tuple("40555555Y", "María López"),
                new Tuple("40666666M", "Javier Castilla")
        );
        Dictionary grades = new Dictionary(
                new Tuple("40953",
                new Dictionary(
                        new Tuple("40444444X", 7.5),
                        new Tuple("40555555Y", 4.0)
                )),
                new Tuple("12345",
                new Dictionary(
                        new Tuple("40444444X", 5.5),
                        new Tuple("40555555Y", 8.0),
                        new Tuple("40666666M", 7.0)
                ))
        );

        System.out.println("REAL EXAMPLE OF THE USE OF DICTIONARIES"
                + "Visualizing the created dictionaries separately");
        System.out.println(subjects + "\n");
        System.out.println(students + "\n");
        System.out.println(grades + "\n");
        System.out.println(SEPARATOR);

        // Tuple containing all the information
        Tuple tuple = new Tuple(subjects, students, grades);

        Dictionary result1 = gradesExample(tuple, "12345", true);
        Dictionary result2 = gradesExample(tuple, "40953", true);
        Dictionary result3 = gradesExample(tuple, "12345", false);
        Dictionary result4 = gradesExample(tuple, "40953", false);

        System.out.println("\nVisualizing the RESULT1\n" + result1);
        System.out.println("\nVisualizing the RESULT2\n" + result2);
        System.out.println("\nVisualizing the RESULT3\n" + result3);
        System.out.println("\nVisualizing the RESULT4\n" + result4);

        // Make tests with main methods and prints result in csv format.
        // Parameter notes millions of elements to test.
        //TimesTesting.doTestPut(2);
        //TimesTesting.doTestGet(2);
        //TimesTesting.doTestPop(2);
    }

    /**
     * Example case of use method. It returns a dictionary containing the
     * students with the specified grades from a subject with a given id.
     *
     * @param information
     * @param subjectId
     * @param passedGrades
     * @return new dictionary with filtered students
     */
    public static Dictionary gradesExample(
            Tuple information, String subjectId, boolean passedGrades
    ) {
        Dictionary studentsGrades = (Dictionary) (
                (Dictionary) information.get(2)
        ).get(subjectId);

        Dictionary result = new Dictionary();
        for (Object dni : studentsGrades) {
            double grade = (Double) studentsGrades.get(dni);
            if (passedGrades && grade >= 5 || !passedGrades && grade < 5) {
                result.put(
                        ((Dictionary) information.get(1)).get(dni),
                        grade
                );
            }
        }

        return result;
    }
}
