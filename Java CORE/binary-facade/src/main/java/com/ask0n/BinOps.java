package com.ask0n;

public class BinOps {
    public String sum(String a, String b) {
        int arg1 = Integer.parseInt(a, 2);
        int arg2 = Integer.parseInt(b, 2);
        int result = Integer.sum(arg1, arg2);
        return Integer.toBinaryString(result);
    }

    public String mult(String a, String b) {
        int arg1 = Integer.parseInt(a, 2);
        int arg2 = Integer.parseInt(b, 2);
        int result = Math.multiplyExact(arg1, arg2);
        return Integer.toBinaryString(result);
    }
}
