package com.example.semesc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.example.semesc.eTypKey.GPS;
import static com.example.semesc.eTypKey.NAZEV;

public class Pamatky implements IPamatky {
    private final String path = "data_c.csv";
    ABSTRTABLEImpl tree = new ABSTRTABLEImpl();
    private eTypKey currentKey = NAZEV;



    @Override
    public int importDatZTEXT() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(this.path));
            Generator generator = new Generator();
            String line = "";
            int i = 0;
            int cycles = 0;
            while ((line = br.readLine()) != null){
                if (i == 0) {
                    i++;
                    continue;
                }

                String[] data = line.split(";");
                if (cycles < 10) {
                    double n = Double.parseDouble(data[0].replace(",", "."));
                    double e = Double.parseDouble(data[1].replace(",", "."));
                    Zamek zamek = new Zamek(generator.generujIdZamku(), data[2], new Gps(n, e));
                    vlozZamek(zamek);
                }
                cycles++;
            }
            return cycles;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public void vlozZamek(Zamek zamek) {
        if (zamek == null) {
            return;
        }
        switch (this.currentKey) {
            case NAZEV -> {
                tree.vloz(zamek.getNazev(), zamek);
            }
            case GPS -> {
                tree.vloz(zamek.getGps().getId(), zamek);
            }
        }
    }

    @Override
    public Zamek najdiZamek(String klic) {
        if (klic == null) {
            return null;
        }
        switch (this.currentKey) {
            case NAZEV -> {
                return (Zamek) tree.najdi(klic);
            }
            case GPS -> {
                String[] data = klic.split(";");
                Gps gps = new Gps(Double.parseDouble(data[0]), Double.parseDouble(data[1]));
                return (Zamek) tree.najdi(gps.getId());
            }
        }

        return null;
    }

    @Override
    public Zamek odeberZamek(String klic) {
        if (klic == null) {
            return null;
        }

        switch (this.currentKey) {
            case NAZEV -> {
                return (Zamek) tree.odeber(klic);
            }
            case GPS -> {
                String[] data = klic.split(";");
                Gps gps = new Gps(Double.parseDouble(data[0]), Double.parseDouble(data[1]));
                return (Zamek) tree.odeber(gps.getId());
            }
        }
        return null;
    }

    @Override
    public void zrus() {
        tree.zrus();
    }

    @Override
    public void prebuduj() {
        List<Zamek> list = new ArrayList<>();
        Iterator<Zamek> iterator = tree.vytvorIterator(eTypProhl.HLOUBKY);
        while (iterator.hasNext()){
            list.add(iterator.next());
        }
        zrus();
        switch (this.currentKey) {
            case NAZEV -> {
                list.sort((o1, o2) -> o1.getNazev().compareTo(o2.getNazev()));
            }
            case GPS -> {
                list.sort((o1, o2) -> Long.compare(o1.getGps().getId(), o2.getGps().getId()));
            }
        }
        prebuduj(list);
    }

    private void prebuduj(List<Zamek> list) {
        if (list.isEmpty()) {
            return;
        }
        if (list.size() == 1) {
            vlozZamek(list.get(0));
            return;
        }
        int prostredek = list.size()/2;
        vlozZamek(list.get(prostredek));
        prebuduj(list.subList(0, prostredek));
        prebuduj(list.subList(prostredek, list.size()));
    }

    @Override
    public void nastavKlic(eTypKey typ) {
        this.currentKey = typ;
    }

    @Override
    public Zamek najdiNejbliz(String klic) {
        switch (this.currentKey) {
            case NAZEV -> {
                return null;
            }
            case GPS -> {
                Iterator<Zamek> iterator = tree.vytvorIterator(eTypProhl.HLOUBKY);
                Zamek temp1 = iterator.next();
                Zamek temp2 = iterator.next();
                Zamek closestZ = temp1;
                long closest = Math.abs(temp1.gps.getId() - Long.parseLong(klic));
                long tempC = Math.abs(temp2.gps.getId() - Long.parseLong(klic));
                Zamek temp3 = null;
                while (iterator.hasNext()){
                    if (closest > tempC) {
                        closest = tempC;
                        if (temp3 == null) {
                            closestZ = temp2;
                        } else {
                            closestZ = temp3;
                        }
                    }
                    temp3 = iterator.next();
                    tempC = Math.abs(temp3.gps.getId() - Long.parseLong(klic));
                    if (!iterator.hasNext()) {
                        if (closest > tempC) {
                            closestZ = temp3;
                        }
                    }
                }
                return closestZ;
            }
        }
        return null;
    }

    @Override
    public Iterator vytvorIterator(eTypProhl typ) {
        return tree.vytvorIterator(typ);
    }

    public void generuj(){
        Generator generator = new Generator();
        for (int i = 0; i < 6; i++) {
            vlozZamek(generator.generuj());
        }
    }

    public void zapisDoSouboru() throws IOException {
        ListDoSouboru list = new ListDoSouboru(vytvorIterator(eTypProhl.HLOUBKY));
        list.listDoBina();
    }

}
