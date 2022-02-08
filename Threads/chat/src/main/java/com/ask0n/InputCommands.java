package com.ask0n;

import org.apache.log4j.Logger;

import java.util.Scanner;

public class InputCommands implements Runnable{
    private static final Logger log = Logger.getLogger(InputCommands.class);
    private static final String STOP_COMMAND = "/stop";
    private final Server server;

    public InputCommands(Server server) {
        this.server = server;
    }

    @Override
    public void run() {
        String command;
        while ((command = readLine()) != null){
            log.info("Пользователь ввел команду - " + command);
            switch (command){
                case STOP_COMMAND:
                    server.stop();
                    break;
                default:
                    System.out.println("Command not found");
                    break;
            }
        }
    }

    private static String readLine() {
        Scanner scanner = new Scanner(System.in);
        return scanner.hasNext() ? scanner.nextLine() : null;
    }
}
