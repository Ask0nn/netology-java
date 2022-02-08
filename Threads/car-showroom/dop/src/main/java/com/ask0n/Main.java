package com.ask0n;

public class Main {
    public static final int maxNumOfCars = 10;

    public static void main(String[] args) throws InterruptedException {
        final CarShowroom carShowroom = new CarShowroom();
        Customer c1 = new Customer(carShowroom, 5);
        Customer c2 = new Customer(carShowroom, 1);
        Customer c3 = new Customer(carShowroom, 5);

        new Thread(null, c1, "Покупатель 1").start();
        new Thread(null, c2, "Покупатель 2").start();
        new Thread(null, c3, "Покупатель 3").start();

        Thread.sleep(3000);
        int numOfCars = c1.getNumOfPurchase() + c3.getNumOfPurchase() + c2.getNumOfPurchase();
        try {
            carShowroom.getLock().lock();
            while (carShowroom.getNumOfSoldCars() < maxNumOfCars && carShowroom.getNumOfSoldCars() < numOfCars) {
                new Thread(null, new Manufacturer(carShowroom), "Производитель").start();
                carShowroom.getCondition().await();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            carShowroom.getLock().unlock();
        }
        System.exit(0);
    }
}
