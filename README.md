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
        
        // Creates a dictionary via other dictionary
        Dictionary otherDict = new Dictionary(myDict);
        System.out.println(anotherDict.equals(myDict)); // Will print true
        
        // Cereates a dictionary with dynamic number of arguments
        Dictionary otherDict2 = new Dictionary(1, 2, 3, 4);
        System.out.println(otherDict2); // Will print {1: 2, 3: 4}

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
        // Create a basic tuple
        Tuple myTuple = new Tuple(1, 2, 3, 4);

        // Get an element from the tuple (via index)
        System.out.println(myTuple.get(0)); // Will print 1
        
        // Create a tuple via iterable object
        Tuple otherTuple = new Tuple(myTuple);
        System.out.println(otherTuple.equals(myTuple));  // Will print true
}
```

For a more real example, see Main Class contained in org.ulpgc.edp.control package.

## Documentation

To see full documentation of both classes visit the [Official Documentation Website](https://javier-castilla.github.io/Java-own-Python-dictionary-implementation-DOCUMENTATION/)

## Authors

Developed by **Javier Castilla** with help of
- David Miranda
- Esteban Trujillo
- Elena Artiles