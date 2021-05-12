package com.revature.util;

import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * List
 * <p>
 * The interface for the data structure used for this project, as we are not allowed to use the data structures from java.util.Collections at all.
 *
 * @param <E> The object type the list will hold
 */
public interface List<E> extends Iterable<E> {

    default Stream<E> stream() { //we streamin' now bois
        return StreamSupport.stream(spliterator(), false);
    }

    /**
     * Adds an object to the back of the list.
     *
     * @param e The object to be added
     */
    void add(E e);

    /**
     * Returns the object at the specified position of the list (zero-based)
     *
     * @param index The position of the object to be retrieved
     * @return The object from the list
     */
    E get(int index);

    /**
     * Reports the number of elements inside of the list.
     *
     * @return The number of elements in the list as an integer
     */
    int size();

    /**
     * Reports if the list is empty.
     *
     * @return true if the list is empty, false if not
     */
    boolean isEmpty();

    /**
     * Removes an element from the specified index
     *
     * @param index The specified index
     */
    void removeAt(int index);

}
