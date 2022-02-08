package com.ask0n;

import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.util.Arrays;
import java.util.Scanner;

public class Client {
    private static final Logger log = Logger.getLogger(Client.class);
    private static final String EXIT_COMMAND = "/exit";
    private static final Settings settings = Settings.getInstance();
    private BufferedReader in;
    private PrintWriter out;
    private InputChannel inputChannel;
    private String username;
    private Socket socket;

    public Client(String username) {
        try {
            this.username = username;
            socket = new Socket(settings.getHost(), settings.getPort());
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            inputChannel = new InputChannel(in);
            log.info("Введено имя пользователя - " + username);
            sendMessage(username);
        } catch (IOException e) {
            e.printStackTrace(System.err);
        }
    }

    public void start() {
        inputChannel.start();
        log.info("имя пользователя отправлено на сервер и запущен поток принимающий сообщения от сервера");
        String message;
        while ((message = readLine()) != null) {
            if (message.equals(EXIT_COMMAND)) break;
            sendMessage(message);
            log.info("Пользователь отправил сообщение - " + message);
        }
        sendMessage(message);
        log.info("Пользователь отправил сообщение - " + message);
        disconnect();
    }

    public void sendMessage(String newMessage) {
        out.println(newMessage);
    }

    public void disconnect() {
        try {
            inputChannel.interrupt();
            if (socket.isClosed()) {
                in.close();
                out.close();
                socket.close();
            }
        } catch (SocketException e) {
            System.out.println("Вы вышли из чата");
            System.out.println(e.getMessage());
            log.info("Пользователь покинул чат");
            log.info(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace(System.err);
            log.error(Arrays.toString(e.getStackTrace()));
        }
    }

    private static String readLine() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    public BufferedReader getInput() {
        return in;
    }
    public PrintWriter getOutput() {
        return out;
    }
}
