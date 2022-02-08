package com.ask0n;

public class Manufacturer implements Runnable {
    private static final String producedMessage = "Производитель Toyota выпустил 1 авто";

    private final CarShowroom carShowroom;

    public Manufacturer(CarShowroom carShowroom){
        this.carShowroom = carShowroom;
    }

    @Override
    public void run() {
        carShowroom.receiveCar();
        System.out.println(producedMessage);
    }
}
