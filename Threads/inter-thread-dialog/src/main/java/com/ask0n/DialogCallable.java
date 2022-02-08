package com.ask0n;

import java.util.Random;
import java.util.concurrent.Callable;

public class DialogCallable implements Callable<Integer> {
    private int count = 0;
    private final int random = new Random().nextInt(10 + 1 - 3) + 3;

    @Override
    public Integer call(){
        String name = Thread.currentThread().getName().replace("pool-1-thread-", "");
        try {
            for (int i = 0; count < random; count++){
                Thread.sleep(2500);
                System.out.printf("Я поток %s. Всем привет!\n", name);
            }
        } catch (InterruptedException e) {
            System.err.println(e.getMessage());
        } finally{
            System.out.printf("%s завершен\n", name);
        }
        return count;
    }
}
