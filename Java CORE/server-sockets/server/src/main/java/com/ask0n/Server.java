package com.ask0n;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    static final int PORT = 9595;

    private static Socket clientSocket;
    private static ServerSocket serverSocket;
    private static BufferedReader in;
    private static PrintWriter out;

    public static void main(String[] args) {
        try{
            try{
                serverSocket = new ServerSocket(PORT);
                System.out.println("Сервер запущен");
                clientSocket = serverSocket.accept();
                try{
                    in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    out = new PrintWriter(clientSocket.getOutputStream(), true);

                    final String name = in.readLine();
                    System.out.println("Новое соединение");

                    out.println(String.format("Привет %s, твой порт %d", name, clientSocket.getPort()));
                    out.flush();
                }
                finally {
                    clientSocket.close();
                    in.close();
                    out.close();
                }
            }
            finally {
                serverSocket.close();
                System.out.println("Сервер выключен");
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
