package com.ask0n.install;

import com.ask0n.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class Install {
    private static final List<String> dirPaths = Arrays.asList(
            "Games\\savegames","Games\\temp",
            "Games\\src\\main", "Games\\src\\test",
            "Games\\res\\drawables", "Games\\res\\vectors", "Games\\res\\icons");

    public static void main(String[] args) {
        Logger.logInfo("Начало установки\n");
        createDirs();
        createFiles();
        Logger.logInfo("Установка завершена\n\n");
        Logger.saveLog();
    }

    private static void createDirs(){
        Logger.logInfo("Создание каталогов:\n");
        for (var path : dirPaths) {
            try {
                if (Files.createDirectories(Paths.get(path)) != null)
                    Logger.logInfo("\t- Создание \'" + path + "\' выполнено\n");
                else
                    Logger.logInfo("\t- Ошибка при создании \'" + path + "\'\n");
            } catch (IOException e) {
                Logger.logInfo(Arrays.toString(e.getStackTrace()));
                System.err.println(e.getMessage());
            }
        }
    }

    private static void createFiles(){
        Logger.logInfo("Создание файлов:\n");
        File mainFile = new File("Games\\src\\main\\Main.java");
        File utilsFile = new File("Games\\src\\main\\Utils.java");
        try {
            if (mainFile.exists())
                Logger.logInfo("\t- Файл \'" + mainFile.getName() + "\' уже существует\n");
            else{
                if (mainFile.createNewFile())
                    Logger.logInfo("\t- Файл \'" + mainFile.getName() + "\' создан\n");
                else
                    Logger.logInfo("\t- Ошибка при создании \'" + mainFile.getName() + "\'\n");
            }
            if (utilsFile.exists())
                Logger.logInfo("\t- Файл \'" + utilsFile.getName() + "\' уже существует\n");
            else{
                if (utilsFile.createNewFile())
                    Logger.logInfo("\t- Файл \'" + utilsFile.getName() + "\' создан\n");
                else
                    Logger.logInfo("\t- Ошибка при создании \'" + utilsFile.getName() + "\'\n");
            }
        } catch (IOException e) {
            Logger.logInfo(Arrays.toString(e.getStackTrace()));
        }
    }
}
