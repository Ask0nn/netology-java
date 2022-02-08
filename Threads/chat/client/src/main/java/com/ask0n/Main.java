package com.ask0n;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Введите имя: ");
        String username = readLine();

        Client client = new Client(username);
        client.start();
    }

    private static String readLine() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }
}
