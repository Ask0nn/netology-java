package com.ask0n;

import java.io.*;
import java.net.Socket;

public class Client {
    private static final String IP = "localhost";
    private static final int PORT = 9595;

    private static Socket clientSocket;
    private static BufferedReader in;
    private static PrintWriter out;

    public static void main(String[] args) {
        try {
            try {
                clientSocket = new Socket(IP, PORT);
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                out = new PrintWriter(clientSocket.getOutputStream(), true);

                System.out.println("Введите свое имя:");
                String word = reader.readLine();
                out.write(word + "\n");
                out.flush();
                String serverWord = in.readLine();
                System.out.println(serverWord);
            } finally {
                clientSocket.close();
                in.close();
                out.close();
                System.out.println("Соединение завершено");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
