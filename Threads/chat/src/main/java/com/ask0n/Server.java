package com.ask0n;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private static final Logger log = Logger.getLogger(Server.class);
    private ServerSocket serverSocket;
    private static final Settings settings = Settings.getInstance();
    private static final ExecutorService connections = Executors.newFixedThreadPool(64);
    private final List<User> users = new ArrayList<>();

    public Server() {
        try {
            serverSocket = new ServerSocket(settings.getPort());
        } catch (IOException e) {
            e.printStackTrace(System.err);
        }
    }

    public void start() {
        log.info("Сервер запущен, порт = " + settings.getPort());
        System.out.println("Server started, port = " + settings.getPort());
        new Thread(new InputCommands(this)).start();
        new Thread(() -> {
            while (true) {
                try {
                    Socket socket = serverSocket.accept();
                    User user = new User(socket, this);
                    users.add(user);
                    connections.submit(user);
                } catch (IOException e) {
                    e.printStackTrace(System.err);
                }
            }
        }).start();

    }

    public void sendMessageToAll(String message) {
        if (!users.isEmpty())
            users.forEach(user -> user.sendMessage(message));
    }

    public void removeUser(User user) {
        users.remove(user);
    }

    public void stop() {
        log.info("Сервер остановлен");
        System.out.println("Server stop");
        sendMessageToAll("Server stop");
        connections.shutdown();
        System.exit(0);
    }

    public List<User> getUsers() {
        return users;
    }
}
