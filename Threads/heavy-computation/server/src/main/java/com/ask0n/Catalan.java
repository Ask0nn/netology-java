package com.ask0n;

public class Catalan {
    private final int n;

    public Catalan(int n) {
        this.n = n;
    }

    public long getNumber() {
        long[] catalanNumbers = new long[n + 1];

        catalanNumbers[0] = 1;
        for (int i = 0; i < n; i++) {
            catalanNumbers[i + 1] = catalanNumbers[i] * 2 * (2L * i + 1) / (i + 2);
        }

        return catalanNumbers[n];
    }
}
