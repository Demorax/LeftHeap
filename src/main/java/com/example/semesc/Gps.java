package com.example.semesc;

public class Gps {
    long id;
    final double n;
    final double e;

    public Gps(double n, double e) {
        this.n = n;
        this.e = e;
        createId();
    }

    public long getId() {
        return id;
    }

    public double getN() {
        return n;
    }

    public double getE() {
        return e;
    }

    private void createId(){
        int iN = (int) (this.n * Math.pow(10, 6));
        int iE = (int) (this.e * Math.pow(10, 6));
        String sN = Integer.toBinaryString(iN);
        String sE = Integer.toBinaryString(iE);
        int sLength = 0;
        if (sN.length() > sE.length()) {
            sLength = sN.length();
        } else {
            sLength = sE.length();
        }

        String binary = "";
        for (int i = 0; i < sLength; i++) {
            if (i >= sE.length()) {
                binary += String.valueOf(sN.charAt(i)) + "0";
            } else if (i >= sN.length()) {
                binary += "0" + String.valueOf(sE.charAt(i));
            } else {
                binary += String.valueOf(sN.charAt(i)) + String.valueOf(sE.charAt(i));
            }
        }

        long exponent = 1;
        for (int i = binary.length() - 1; i >= 0; i--) {
            this.id += Integer.valueOf(binary.charAt(i)) * exponent;
            exponent *= 2;
        }
    }

    @Override
    public String toString() {
        return "Gps{" +
                "id=" + id +
                ", n=" + n +
                ", e=" + e +
                '}';
    }
}
