package com.ask0n;

public class Box{
    public static final int DELAY = 1000; //1s

    private volatile boolean level = false;

    public void enableLevel(){
        if (!level) {
            level = true;
            printStatus();
        }

    }

    public void disableLevel(){
        if (level) {
            level = false;
            printStatus();
        }
    }

    public void printStatus(){
        if (level)
            System.out.println("Рычаг включен " + level);
        else
            System.out.println("Рычаг выключен " + level);
    }

    public void run() {
        try {
            while (true) {
                Thread.sleep(DELAY);
                disableLevel();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
