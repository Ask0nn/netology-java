package com.ask0n;

import java.util.concurrent.LinkedBlockingQueue;

public class Specialist implements Runnable {
    private static final int PROCESSING_TIME = 3000; //3s
    private final LinkedBlockingQueue<String> queue;
    private final String specialistId;

    public Specialist(LinkedBlockingQueue<String> queue, String specialistId) {
        this.queue = queue;
        this.specialistId = specialistId;
    }

    @Override
    public void run() {
        String call;
        while (!queue.isEmpty()) {
            try {
                while ((call = queue.poll()) != null) {
                    Thread.sleep(PROCESSING_TIME);
                    System.out.println("Специалист (" + specialistId + ") обработал звонок: " + call);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
