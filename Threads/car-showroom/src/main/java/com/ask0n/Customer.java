package com.ask0n;

import java.util.Random;

public class Customer implements Runnable {
    private static int count = 0;

    private final int thisNum;
    private final int numOfPurchase;
    private final CarShowroom carShowroom;

    public Customer(CarShowroom carShowroom) {
        this.carShowroom = carShowroom;
        count++;
        thisNum = count;
        numOfPurchase = new Random().nextInt(6 + 1 - 4) + 4;
    }

    public Customer(CarShowroom carShowroom, int numOfPurchase) {
        this.carShowroom = carShowroom;
        count++;
        thisNum = count;
        this.numOfPurchase = numOfPurchase;
    }

    public String goMessage() {
        return String.format("Покупатель %s зашел в автосалон", thisNum);
    }

    public String buyMessage() {
        return String.format("Покупатель %s уехал на новеньком авто", thisNum);
    }

    public int getNumOfPurchase() {
        return numOfPurchase;
    }

    @Override
    public void run() {
        carShowroom.buyCar(this);
    }
}
