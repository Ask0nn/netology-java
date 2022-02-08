package com.ask0n;

import java.util.Iterator;
import java.util.Random;

public class Randoms implements Iterable<Integer> {
    private Random random;
    private int min;
    private int max;

    public Randoms(int min, int max) {
        random = new Random();
        this.min = min;
        this.max = max;
    }

    /**
     * Если я поавильно понимаю, то hasNext в данном случае всегда будет true.
     * Так как Random будет выдавать числа всегда.
     */
    @Override
    public Iterator<Integer> iterator() {
        return new Iterator<Integer>() {
            @Override
            public boolean hasNext() {
                return true;
            }

            @Override
            public Integer next() {
                return random.ints(min, max + 1)
                        .findFirst()
                        .getAsInt();
            }
        };
    }
}
