package com.revature.util;


public interface List<E> extends Iterable<E>{


    void add(E e);

    E get(int index);

    int size();

    void removeAt(int index);

}
