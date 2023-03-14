package com.example.semesc;

import java.util.Iterator;
import com.example.semesc.Zamek;

public interface AbstrHeap {
    void vybuduj(Zamek[] arr);

    void prebuduj(Zamek[] arr, int i);

    void zrus();

    boolean jePrazdny();

    void vloz(Zamek item);

    Zamek odeberMax();

    Zamek zprusupniMax();

    Iterator vytvorIterator(eTypProhl typ);
}
