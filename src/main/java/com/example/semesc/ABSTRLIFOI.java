package com.example.semesc;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ABSTRLIFOI<T> implements IABSTRLIFO<T> {

    public Node first;
    public int size;

    public ABSTRLIFOI(){
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
        this.first.next = null;
    }

    @Override
    public boolean jePrazdny() {
        return this.size == 0;
    }

    @Override
    public void vloz(T data) {
        Node temp = new Node(data);
        Node currFirst = this.first;
        this.first = temp;
        temp.next = currFirst;
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
                if (temp == null) {
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
