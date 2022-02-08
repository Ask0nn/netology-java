package com.ask0n;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.Collectors;

public class Shop implements Runnable{
    private final List<Long> longs;
    private final LongAdder longAdder;

    public Shop(LongAdder longAdder){
        this.longs = new Random()
                .longs(10, 0, 1024)
                .boxed()
                .collect(Collectors.toList());
        this.longAdder = longAdder;
    }

    @Override
    public void run() {
        longs.forEach(longAdder::add);
    }
}
