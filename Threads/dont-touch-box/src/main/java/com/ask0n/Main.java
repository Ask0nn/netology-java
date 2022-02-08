package com.ask0n;

public class Main {
    public static void main(String[] args) {
        Box box = new Box();
        User user = new User(box);

        new Thread(null, user, "Пользователь").start();
        new Thread(null, box::run, "Box").start();
    }
}