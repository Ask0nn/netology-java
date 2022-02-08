package com.ask0n;

public class DialogThread extends Thread{

    @Override
    public void run(){
        String name = getName().replace("Thread-", "");
        try {
            while(!isInterrupted()) {
                Thread.sleep(2500);
                System.out.printf("Я поток %s. Всем привет!\n", name);
            }
        } catch (InterruptedException e) {
            System.err.println(e.getMessage());
        } finally{
            System.out.printf("%s завершен\n", name);
        }
    }
}
