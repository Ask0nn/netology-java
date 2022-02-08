package com.ask0n;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAdder;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        LongAdder total = new LongAdder();

        ExecutorService executorService = Executors.newFixedThreadPool(3);
        executorService.submit(new Shop(total));
        executorService.submit(new Shop(total));
        executorService.submit(new Shop(total));

        executorService.shutdown();

        boolean finished = executorService.awaitTermination(1, TimeUnit.MINUTES);
        if (finished) System.out.println(total.sum());
    }
}
