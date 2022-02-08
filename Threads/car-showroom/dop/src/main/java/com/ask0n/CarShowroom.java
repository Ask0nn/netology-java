package com.ask0n;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class CarShowroom {
    private static final int carProduceTime = 5000; //5s
    private static final int carSellTime = 2000; //2s

    public static final String noCarMessage = "Машин нет";

    private final List<Object> cars = new ArrayList<>();
    private int numOfSoldCars = 0;
    private final ReentrantLock lock = new ReentrantLock(true);
    private final Condition condition = lock.newCondition();

    public int getNumOfSoldCars() {
        return numOfSoldCars;
    }
    public ReentrantLock getLock() {
        return lock;
    }
    public Condition getCondition() {
        return condition;
    }

    public void receiveCar() {
        try {
            lock.lock();
            Thread.sleep(carProduceTime);
            cars.add(new Object());
            condition.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void buyCar(Customer customer) {
        for (int i = 0; i < customer.getNumOfPurchase(); i++)
            try {
                lock.lock();
                System.out.println(customer.goMessage());
                if (cars.isEmpty()) System.out.println(noCarMessage);
                while (cars.isEmpty()) {
                    condition.await();
                }
                Thread.sleep(carSellTime);
                cars.remove(0);
                numOfSoldCars++;
                System.out.println(customer.buyMessage());
                condition.signalAll();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
    }
}
