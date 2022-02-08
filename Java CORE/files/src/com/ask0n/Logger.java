package com.ask0n;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class Logger {
    private static StringBuilder log = new StringBuilder();
    private static final Path pathToTemp = Paths.get("Games\\temp\\temp.txt");

    public static void logInfo(String info){
        log.append(info);
    }

    public static void saveLog(){
        try {
            if (!Files.exists(pathToTemp))
                Files.createFile(pathToTemp);
            Files.write(pathToTemp, log.toString().getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
