package com.example.semesc;

import java.util.Comparator;

public class SortByGps implements Comparator<Zamek> {

    @Override
    public int compare(Zamek o1, Zamek o2) {
        return (int) (o1.getGps().getId() - o2.getGps().getId());
    }
}
