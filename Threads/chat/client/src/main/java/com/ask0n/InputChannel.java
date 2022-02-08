package com.ask0n;

import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.SocketException;
import java.util.Arrays;

public class InputChannel extends Thread {
    private static final Logger log = Logger.getLogger(InputChannel.class);
    private final BufferedReader in;

    public InputChannel(BufferedReader in) {
        this.in = in;
    }

    @Override
    public void run() {
        try {
            while (!isInterrupted()) {
                String line;
                if ((line = in.readLine()) != null) {
                    System.out.println(line);
                    log.info("Сообщение от сервера - " + line);
                }
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
}
