package com.ask0n;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {
    private int num = 1;
    private String format = "[%s][#%s] %s";
    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

    private static Logger logger;

    private Logger() {}

    public void log(String msg) {
        var dateTime = LocalDateTime.now().format(dateTimeFormatter);
        System.out.println(String.format(format, dateTime, num++, msg));
    }

    public static Logger getInstance() {
        if (logger == null) return logger = new Logger();
        return logger;
    }
}
