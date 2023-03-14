package com.example.semesc;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class AbstrHeapI implements AbstrHeap {
    private Zamek[] array;
    private int size = 0;

    private double n;
    private double e;
    private long id;

    public AbstrHeapI() {
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.array = new Zamek[size];
        this.size = 0;
    }

    public void setN(double n) {
        this.n = n;
    }

    public void setE(double e) {
        this.e = e;
    }

    @Override
    public void vybuduj(Zamek[] arr) {
        this.size = arr.length;
        for (int i = arr.length / 2 ; i >= 1; i--) {
            prebuduj(arr, i);
        }

        for (int i = arr.length - 2; i >= 0; i--) {
            Zamek tmp = arr[0];
            arr[0] = arr[i];
            arr[i] = tmp;
            prebuduj(arr,0);
        }
    }


    @Override
    public void prebuduj(Zamek[] arr, int i) {
        int large = i;
        int left = 2 * i;
        int right = 2 * i + 1;
        if (left <= arr.length-1 && (Math.abs(arr[left].getGps().n - this.n) + Math.abs(arr[left].getGps().e - this.e))
                < (Math.abs(arr[large].getGps().n - this.n) + Math.abs(arr[large].getGps().e - this.e))) {
            large = left;
        } else {
            large = i;
        }

        if (right <= arr.length-1 && (Math.abs(arr[right].getGps().n - this.n) + Math.abs(arr[right].getGps().e - this.e))
                < (Math.abs(arr[large].getGps().n - this.n) + Math.abs(arr[large].getGps().e - this.e))) {
            large = right;
        }

        if (large != i) {
            swap(large, i);
            prebuduj(arr, large);
        }
        this.array = arr;

    }

    @Override
    public void zrus() {
        this.array = null;
        this.size = 0;
        this.e = 0;
        this.n = 0;
    }

    @Override
    public boolean jePrazdny() {
        return this.size == 0;
    }
    private int parent(int pos)  {
        return pos / 2;
    }
    private void swap(int first, int second)  {
        Zamek tmp;
        tmp = this.array[first];
        this.array[first] =  this.array[second];
        this.array[second] = tmp;
    }

    @Override
    public void vloz(Zamek item) {
        if (jePrazdny()) {
            setSize(1);
            this.array[0] = item;
        } else {
            this.array = Arrays.copyOf(array, array.length + 1);
            this.array[size++] = item;
            int current = size - 1;
            while (Math.abs((this.array[current].getGps().n - this.n) + (this.array[current].getGps().e - this.e)) <
                    Math.abs((this.array[parent(current)].getGps().n - this.n) + (this.array[parent(current)].getGps().e - this.e))) {
                swap(current, parent(current));
                current = parent(current);
            }
        }
    }


    @Override
    public Zamek odeberMax() {
        if (jePrazdny()) {
            return null;
        } else if (this.array.length == 1){
            Zamek min = this.array[0];
            this.array = null;
            return min;
        }
        Zamek min = this.array[0];
        Zamek lastItem = this.array[this.array.length - 1];
        this.array = Arrays.copyOf(array, array.length - 1);
        this.array[0] = lastItem;
        prebuduj(this.array, 0);
        return min;
    }

    @Override
    public Zamek zprusupniMax() {
        return this.array[0];
    }

    @Override
    public Iterator vytvorIterator(eTypProhl typ) {
        ABSTRFIFO<Zamek> queueDoHloubky = new ABSTRFIFO<>();
        ABSTRFIFO<Zamek> queueDoSirky = new ABSTRFIFO<>();
        switch (typ){
            case HLOUBKY -> {
                Zamek[] doHloubkyPole = Arrays.copyOf(array, array.length);
                Arrays.sort(doHloubkyPole, new SortByGps());
                for (Zamek j : doHloubkyPole)
                    queueDoHloubky.vloz(j);
                return new Iterator() {
                @Override
                public boolean hasNext() {
                    if (queueDoHloubky.jePrazdny()) {
                        return false;
                    }
                    return true;
                }

                @Override
                public Object next() {
                    if (!hasNext()) {
                        throw new NoSuchElementException();
                    }

                    Zamek current = queueDoHloubky.odeber();
                    return current;
                }

            };

            }
            case SIRKY -> {
                for (Zamek j : this.array)
                    queueDoSirky.vloz(j);
                return new Iterator() {

                    @Override
                    public boolean hasNext() {
                        if (queueDoSirky.jePrazdny()) {
                            return false;
                        }
                        return true;
                    }

                    @Override
                    public Object next() {
                        if (!hasNext()) {
                            throw new NoSuchElementException();
                        }

                        Zamek current = queueDoSirky.odeber();
                        return current;
                    }

                };
            }
        }

        return null;
    }

}
