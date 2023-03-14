package com.example.semesc;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Generator {

    public Generator() {

    }

    public Zamek generuj(){
        int idZamek = generujIdZamku();
        String nazev = generujNazev();
        double n = generujN();
        double e = generujE();

        return new Zamek(idZamek, nazev, new Gps(n, e));
    }

    public int generujIdZamku(){
        Random random = new Random();
        int min = 1111;
        int max = 9999;
        int idZamek = Math.abs(min+ (max - min) * random.nextInt());
        return idZamek;
    }
    public String generujNazev(){
        String[] list = {"Trutnov", "Telc", "Praha", "Most", "Chomutov",
                "Jirkov", "Plzen", "Olomouc", "Brno"};
        int random = (int) (Math.random() * (list.length));
        return list[random];
    }

    public double generujN(){
        Random random = new Random();
        double min = 45;
        double max = 51;
        double n = (min+ (max - min) * random.nextDouble());
        return n;
    }

    public double generujE(){
        Random random = new Random();
        double min = 10;
        double max = 20;
        double e = (min+ (max - min) * random.nextDouble());
        return e;
    }

}
