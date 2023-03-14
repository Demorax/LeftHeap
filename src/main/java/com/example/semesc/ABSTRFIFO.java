package com.example.semesc;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ABSTRFIFO<T> implements IABSTRFIFO<T> {
    private Node first;
    private Node last;
    private int size;

    public ABSTRFIFO() {
        this.size = 0;
    }

    private class Node {
        private T data;
        private Node next;

        private Node(T data){
            this.data = data;
        }
    }

    @Override
    public void zrus() {
        this.size = 0;
        this.first = null;
        this.last = null;
        this.last.next = null;
    }

    @Override
    public boolean jePrazdny() {
        return this.size == 0;
    }

    @Override
    public void vloz(T data) {
        Node temp = new Node(data);
        if (jePrazdny()) {
            this.first = temp;
            this.last = temp;
            size++;
            return;
        }
        this.last.next = temp;
        this.last = temp;
        size++;

    }

    @Override
    public T odeber() {
        if (jePrazdny()) {
            return null;
        }
        T tempData = this.first.data;
        this.first = this.first.next;
        size--;
        return tempData;
    }

    @Override
    public Iterator vytvorIterator() {
        return new Iterator() {
            Node temp = first;
            boolean end = false;
            @Override
            public boolean hasNext() {
                if (temp == last) {
                    return false;
                }
                return true;
            }

            @Override
            public Object next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                end = true;
                T data = temp.data;
                temp = temp.next;
                return data;
            }
        };
    }
}
