package com.ask0n;

import java.util.concurrent.LinkedBlockingQueue;

public class PABX implements Runnable {
    private static final int DELAY = 1000; //1s
    private static final int NUM_OF_CALLS = 60;
    private final LinkedBlockingQueue<String> queue;

    public PABX(LinkedBlockingQueue<String> queue){
        this.queue = queue;
    }

    @Override
    public void run() {
        for (int i = 0; i < NUM_OF_CALLS; i++) {
            try {
                Thread.sleep(DELAY);
                System.out.println("Звонок #" + i);
                queue.add("Звонок #" + i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
