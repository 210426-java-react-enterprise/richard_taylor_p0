package com.revature.util;

/**
 * List
 * <p>
 * The interface for the data structure used for this project, as we are not allowed to use java.util.Collections at all.
 *
 * @param <E> The object type the list will hold
 */
public interface List<E> extends Iterable<E> {

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
     * Removes an element from the specified index
     *
     * @param index The specified index
     */
    void removeAt(int index);

}
