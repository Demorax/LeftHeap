package com.example.semesc;

import java.util.Iterator;

public interface IABSTRFIFO<T>{
    void zrus();

    boolean jePrazdny();

    void vloz(T data);

    T odeber();

    Iterator vytvorIterator();

}
