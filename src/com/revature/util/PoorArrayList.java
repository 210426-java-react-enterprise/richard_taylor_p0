package com.revature.util;

import java.util.Arrays;

import java.util.Arrays;

/*
    As the class name suggests, a poor man's (and probably scuffed) implementation of a dynamically sized array.
 */
public class PoorArrayList<E> {
    private Object[] storage;
    private final int DEFAULT_CAPACITY = 10;
    private int size = 0;

    public PoorArrayList() {
        this.storage = new Object[DEFAULT_CAPACITY];
    }

    public  PoorArrayList(int capacity) {
        this.storage = new Object[capacity];
    }

    //Adds an element to the array. If the array is full, increaseCapacity() is called to double the size.
    public void add(E e) {
        if (size == storage.length)
            increaseCapacity();
        storage[size++] = e;
    }

    //Doubles the capacity of the array.
    private void increaseCapacity() {
        int newSize = storage.length * 2;
        this.storage = Arrays.copyOf(storage, newSize);
    }

    //Ensures the index is not out of bounds, then reassigns the object at the index.
    public void change(int index, E e) {
        if (index >= size || index < 0)
            throw new IndexOutOfBoundsException(String.format("Index: %d Size: %d", index, size));
        storage[index - 1] = e;
    }

    public int size() {
        return this.size;
    }

    //Ensures the specified index is not out of bounds, then retrieves the object at the index.
    public E get(int index) {
        if (index >= size || index < 0)
            throw new IndexOutOfBoundsException(String.format("Index: %d Size: %d", index, size));
        return (E) storage[index];
    }

    //Removes the specified index from the array
    public void removeAt(int index) {
        Object[] copy = new Object[storage.length - 1];

        for (int i = 0, j = 0; i < storage.length; i++) {
            if (i != index)
                copy[j++] = storage[i];
        }
        storage = copy;
        size--;
    }
}