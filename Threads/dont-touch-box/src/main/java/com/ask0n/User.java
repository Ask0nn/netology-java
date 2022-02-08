package com.ask0n;

public class User implements Runnable {
    private static final int DELAY = 3000; //3s
    private static final int MAX_ITERATIONS = 15;

    private final Box box;

    public User(Box box){
        this.box = box;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < MAX_ITERATIONS; i++) {
                Thread.sleep(DELAY);
                box.enableLevel();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.exit(0);
    }
}
