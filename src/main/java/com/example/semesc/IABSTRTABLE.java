package com.example.semesc;

import java.util.Iterator;

public interface IABSTRTABLE<K extends Comparable<K>, V>  {
    void zrus();

    boolean jePrazdny();

    V najdi(K key);

    void vloz(K key, V value);

    V odeber(K key);

    Iterator vytvorIterator(eTypProhl typ);


}
