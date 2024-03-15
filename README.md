# Dictionary and Tuple Objects (org.ulpgc.edp.model.* package)

This is a project developed in Java as part of Data Structures and Programming subject belonging to the Computer Science degree
of the U.L.P.G.C. University, in which you can gain access and use two main classes, a Dictionary and a Tuple.

## Dictionary

A Dictionary is a data structure used to store pairs of keys and values in an efficient way, reaching an algorithm
complexity near O(1). Its functionality is simple, link it with the idea of having an index (keys) and some values
associated with them (values).

### Example

```java
import org.ulpgc.edp.model.dct.*;

public static void main(String[] args) {
        // Creates an empty dictionary
        Dictionary myDict = new Dictionary();

        // Insert pairs into the dictionary
        myDict.put("key1", 1);
        myDict.put("key2", 2);

        // Get an element from the dictionary
        System.out.println(myDict.get("key1")); // Will print 1
}

```

## Tuple

A Tuple is an immutable data structure used to store values or items. Once it is created, it cannot be changed.
Useful to store short - medium amount of data that have to stay immutable.

### Example

```java
import org.ulpgc.edp.model.tpl.*;

public static void main(String[] args) {
        // Create a tuple
        Tuple myTuple = new Tuple(1, 2, 3, 4);

        // Get an element from the tuple (via index)
        System.out.println(myTuple.get(0)); // Will print 1
}
```

## Documentation

To see full documentation of both classes visit the [Official Documentation Website](https://javier-castilla.github.io/Java-own-Python-dictionary-implementation-DOCUMENTATION/)

## Authors

Developed by **Javier Castilla** with help of
- David Miranda
- Esteban Trujillo
- Elena Artiles.