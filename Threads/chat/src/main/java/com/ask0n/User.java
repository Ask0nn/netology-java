package com.ask0n;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class User implements Runnable {
    private static final Logger log = Logger.getLogger(User.class);
    private static final String EXIT_COMMAND = "/exit";
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
    private Socket socket;
    private Server server;
    private PrintWriter out;
    private Scanner in;
    private String username;

    public User(Socket socket, Server server) {
        try {
            this.socket = socket;
            this.server = server;
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new Scanner(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace(System.err);
        }
    }

    @Override
    public void run() {
        if (in.hasNext()) {
            username = in.nextLine();
            System.out.println(username + " connected");
            log.info(username + " подключился");
            server.sendMessageToAll(String.format("%s - %s %s",
                    LocalDateTime.now().format(DATE_FORMAT), username, "вошел в чат"));
        }
        while (!Thread.currentThread().isInterrupted()) {
            if (in.hasNext()) {
                String line = in.nextLine();
                if (line.equals(EXIT_COMMAND)) break;
                log.info(username + " отправил сообщение: " + line);
                server.sendMessageToAll(String.format("%s - %s: %s",
                        LocalDateTime.now().format(DATE_FORMAT), username, line));
            }
        }
        disconnect();
    }

    public void sendMessage(String message) {
        out.println(message);
    }

    private void disconnect() {
        try {
            server.removeUser(this);
            server.sendMessageToAll(String.format("%s - %s %s",
                    LocalDateTime.now().format(DATE_FORMAT), username, "покинул чат"));
            if (!socket.isClosed()) {
                in.close();
                out.close();
                socket.close();
            }
            log.info(username + " отключился");
            System.out.println(username + " disconnected");
        } catch (IOException e) {
            e.printStackTrace(System.err);
        }
    }

    public String getUsername() {
        return this.username;
    }
}
