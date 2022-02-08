package com.ask0n;

public class Main {
    public static void main(String[] args) {
        BinOps bins = new BinOps();
        String arg = Integer.toBinaryString(5);
        System.out.println(bins.sum(arg, arg));
        System.out.println();
        System.out.println(bins.mult(arg, arg));

    }
}
