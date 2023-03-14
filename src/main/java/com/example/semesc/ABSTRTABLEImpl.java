package com.example.semesc;;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

public class ABSTRTABLEImpl<K extends Comparable<K>, V> implements IABSTRTABLE<K, V> {

    Node root;
    public class Node {
        K key;
        V name;

        Node leftChild;
        Node rightChild;

        public Node(K key, V name) {
            this.key = key;
            this.name = name;
        }

        public K getKey() {
            return key;
        }

        public V getName() {
            return name;
        }

        public void setKey(K key) {
            this.key = key;
        }

        public void setName(V name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "Klíč: " + key + "jméno: " + name;

        }
    }

    @Override
    public void zrus() {
        this.root = null;
    }

    @Override
    public boolean jePrazdny() {
        return this.root == null;
    }

    @Override
    public V najdi(K key) {
        Node current = root;
        if (jePrazdny()) {
            return null;
        }

        while(current != null) {
            if (key.compareTo(current.key) == 0) {
                return current.getName();
            }

            if (key.compareTo(current.key) < 0) {
                current = current.leftChild;
            } else {
                current = current.rightChild;
            }
        }
        return null;
    }

    @Override
    public void vloz(K key, V value) {
        Node newNode = new Node(key, value);
        if (root == null) {
            root = newNode;
        } else {
            Node temp = root;

            Node parent = null;

            while(true){
                parent = temp;
                if (key.compareTo(temp.key) == 0) {
                    return;
                }
                if (key.compareTo(temp.key) < 0 ) {
                    temp = temp.leftChild;
                    if (temp == null) {
                        parent.leftChild = newNode;
                        return;
                    }
                } else {
                    temp = temp.rightChild;

                    if (temp == null) {
                        parent.rightChild = newNode;
                        return;
                    }
                }
            }
        }
    }

    @Override
    public V odeber(K key) {
        if (jePrazdny()) {
            return null;
        }
        Node parent = null;
        Node current = root;

        while (current != null && key.compareTo(current.key) != 0){
            parent = current;

            if (key.compareTo(current.key) < 0) {
                current = current.leftChild;
            } else {
                current = current.rightChild;
            }

        }
        if (current == null) {
            return root.getName();
        }

        if (current.leftChild == null && current.rightChild == null) {
            if (current != root) {
                if (parent.leftChild == current) {
                    parent.leftChild = null;
                    return current.getName();
                } else {
                    parent.rightChild = null;
                    return current.getName();
                }
            } else {
                return current.getName();
            }
        }

        else if (current.leftChild == null && current.rightChild != null) {
            Node lowestNode = findLowest(current.rightChild);
            Node temp = lowestNode;
            odeber(lowestNode.key);
            current.key = temp.key;
            current.name = temp.name;

        } else if (current.leftChild != null && current.rightChild == null) {
            Node lowestNode = findLowest2(current.leftChild);
            Node temp2 = lowestNode;
            odeber(lowestNode.key);
            current.key = temp2.key;
            current.name = temp2.name;
        }

        else {
            Node onlyChild = null;
            if (current.leftChild == null) {
                onlyChild = current.rightChild;
            } else {
                onlyChild = current.leftChild;
            }

            if (current != root) {
                if (current == parent.leftChild) {
                    parent.leftChild = onlyChild;
                } else {
                    parent.rightChild = onlyChild;
                }
            } else {
                current = onlyChild;
            }
        }
        return current.getName();
    }

    @Override
    public Iterator vytvorIterator(eTypProhl typ) {
        ABSTRLIFOI<Node> stack = new ABSTRLIFOI<>();
        ABSTRFIFO<Node> queue = new ABSTRFIFO<>();
        Node temp = root;
        while (temp != null) {
            stack.vloz(temp);
            temp = temp.leftChild;
        }
        queue.vloz(root);
        switch (typ){
            case HLOUBKY -> {
                return new Iterator() {

                    @Override
                    public boolean hasNext() {
                        if (stack.jePrazdny()) {
                            return false;
                        }
                        return true;
                    }

                    @Override
                    public Object next() {
                        if (!hasNext()) {
                            throw new NoSuchElementException();
                        }

                        Node current = stack.odeber();
                        if (current.rightChild != null) {
                            Node temp = current.rightChild;
                            while (temp != null) {
                                stack.vloz(temp);
                                temp = temp.leftChild;
                            }
                        }
                        return current.getName();
                    }

                };
            }
            case SIRKY -> {
                return new Iterator() {

                    @Override
                    public boolean hasNext() {
                        if (queue.jePrazdny()) {
                            return false;
                        }
                        return true;
                    }

                    @Override
                    public Object next() {
                        if (!hasNext()) {
                            throw new NoSuchElementException();
                        }

                        Node current = queue.odeber();
                        if (current.leftChild != null) {
                            queue.vloz(current.leftChild);
                        }

                        if (current.rightChild != null) {
                            queue.vloz(current.rightChild);
                        }
                        return current.getName();
                    }

                };
            }
        }

        return null;
    }


    private Node findLowest(Node node) {
        if (node.leftChild != null) {
            node = node.leftChild;
        }
        return node;

    }

    private Node findLowest2(Node node) {
        if (node.rightChild != null) {
            node = node.rightChild;
        }
        return node;

    }


}
