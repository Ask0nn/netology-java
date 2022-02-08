package com.ask0n;

import java.util.ArrayList;
import java.util.List;

public class CarShowroom {
    private static final int carProduceTime = 5000; //5s
    private static final int carSellTime = 2000; //2s

    public static final String noCarMessage = "Машин нет";

    private final List<Object> cars = new ArrayList<>();
    private int numOfSoldCars = 0;

    public int getNumOfSoldCars() {
        return numOfSoldCars;
    }

    public synchronized void receiveCar() {
        try {
            Thread.sleep(carProduceTime);
            cars.add(new Object());
            notify();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized void buyCar(Customer customer) {
        for (int i = 0; i < customer.getNumOfPurchase(); i++)
            try {
                System.out.println(customer.goMessage());
                if (cars.isEmpty()) System.out.println(noCarMessage);
                while (cars.isEmpty()) {
                    wait();
                }
                Thread.sleep(carSellTime);
                cars.remove(0);
                numOfSoldCars++;
                System.out.println(customer.buyMessage());
                notifyAll();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
    }
}
