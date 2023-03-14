package com.example.semesc;

public class Zamek {
    int id;
    String nazev;
    Gps gps;

    public Zamek(int id, String nazev, Gps gps) {
        this.id = id;
        this.nazev = nazev;
        this.gps = gps;
    }


    public String getNazev() {
        return nazev;
    }


    public Gps getGps() {
        return gps;
    }

    @Override
    public String toString() {
        return "Zamek{" +
                "id=" + id +
                ", nazev='" + nazev + '\'' +
                ", gps=" + gps +
                '}';
    }
}
