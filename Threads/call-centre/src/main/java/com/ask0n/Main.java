package com.ask0n;

import java.util.concurrent.*;

public class Main {
    private static final int DELAY = 5000; // 5s

    private static LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();

    public static void main(String[] args) throws InterruptedException {
        new Thread(new PABX(queue), "tel").start();

        Thread.sleep(DELAY);

        new Thread(new Specialist(queue, "c1")).start();
        new Thread(new Specialist(queue, "c2")).start();
        new Thread(new Specialist(queue, "c3")).start();
    }
}
