package com.ask0n;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ThreadGroup group = new ThreadGroup("Dialogs");

        DialogThread dialogThread1 = new DialogThread();
        DialogThread dialogThread2 = new DialogThread();
        DialogThread dialogThread3 = new DialogThread();
        DialogThread dialogThread4 = new DialogThread();

        Thread t1 = new Thread(group, dialogThread1, "t1");
        Thread t2 = new Thread(group, dialogThread2, "t2");
        Thread t3 = new Thread(group, dialogThread3, "t3");
        Thread t4 = new Thread(group, dialogThread4, "t4");

        t1.start();
        t2.start();
        t3.start();
        t4.start();

        Thread.sleep(15000);
        group.interrupt();

        ExecutorService pool = Executors.newFixedThreadPool(4);
        List<Callable<Integer>> callableList = new ArrayList<>();

        callableList.add(new DialogCallable());
        callableList.add(new DialogCallable());
        callableList.add(new DialogCallable());
        callableList.add(new DialogCallable());

        final int result = pool.invokeAny(callableList);

        System.out.println("Самая быстрая задача вывела " + result + " сообщений.");

        pool.shutdown();
    }
}
