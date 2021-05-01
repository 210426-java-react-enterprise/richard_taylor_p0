package com.revature.util;

import java.util.Arrays;
import java.util.Iterator;

/**
    PoorArrayList

    As the name suggests, a poor man's implementation of a dynamically sized array, the data structure of choice
    I will be using for this project.
 */
public class PoorArrayList<E> implements Iterable<E> {
    private Object[] storage;
    private final int DEFAULT_CAPACITY = 10;
    private int size = 0;

    public PoorArrayList() {
        this.storage = new Object[DEFAULT_CAPACITY];
    }

    public  PoorArrayList(int capacity) {
        this.storage = new Object[capacity];
    }

    /**
     * Adds an element to the back of the array. Doubles the size of the array if the size is full.
     * @param e The data to be added
     */
    public void add(E e) {
        if (size == storage.length)
            increaseCapacity();
        storage[size++] = e;
    }

    /**
     * Doubles doubles the size of the array.
     */
    private void increaseCapacity() {
        int newSize = storage.length * 2;
        this.storage = Arrays.copyOf(storage, newSize);
    }

    /**
     * Changes the element of the array to the data passed in.
     * @param index The specified index.
     * @param e The new data at the index
     */
    public void change(int index, E e) { //TODO Decide if I even need this method.
        if (index >= size || index < 0)
            throw new IndexOutOfBoundsException(String.format("Index: %d Size: %d", index, size));
        storage[index - 1] = e;
    }

    /**
     *
     * @return The size of the array
     */
    public int size() {
        return this.size;
    }

    /**
     * Gets the element at the specified index.
     * @param index The index to be retrieved.
     * @return The data in the element.
     */
    @SuppressWarnings("unchecked")
    public E get(int index) {
        if (index >= size || index < 0)
            throw new IndexOutOfBoundsException(String.format("Index: %d Size: %d", index, size));
        return (E) storage[index];
    }

    /**
     * Removes the index of the array.
     * @param index The index to be removed
     */
    public void removeAt(int index) { //TODO Decide if I even need this method.
        Object[] copy = new Object[storage.length - 1];

        for (int i = 0, j = 0; i < storage.length; i++) {
            if (i != index)
                copy[j++] = storage[i];
        }
        storage = copy;
        size--;
    }

    //Iterator implementation. Hooray for foreach loops!
    @Override
    public Iterator<E> iterator () {
        Iterator<E> iterator = new Iterator<E>() {
            private int cursor = 0;

            @Override
            public boolean hasNext() {
                return cursor < size && storage[cursor] != null;
            }

            @Override
            @SuppressWarnings("unchecked")
            public E next() {
                return (E) storage[cursor++];
            }
        };
        return iterator;
    }
}