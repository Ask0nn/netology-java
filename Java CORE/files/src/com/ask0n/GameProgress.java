package com.ask0n;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class GameProgress implements Serializable {
    private static final long serialVersionUID = 1L;

    private int health;
    private int weapons;
    private int lvl;
    private double distance;

    public GameProgress(int health, int weapons, int lvl, double distance) {
        this.health = health;
        this.weapons = weapons;
        this.lvl = lvl;
        this.distance = distance;
    }

    @Override
    public String toString() {
        return "GameProgress{" +
                "health=" + health +
                ", weapons=" + weapons +
                ", lvl=" + lvl +
                ", distance=" + distance +
                '}';
    }

    public static void saveGame(String path, GameProgress gameProgress){
        ObjectOutputStream oos = null;
        try{
            Logger.logInfo("Сохранение игрового процесса \'" + path + "\'\n");
            oos = new ObjectOutputStream(new FileOutputStream(path));
            oos.writeObject(gameProgress);

        } catch (IOException e) {
            Logger.logInfo(Arrays.toString(e.getStackTrace()));
        } finally {
            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException e) {
                    Logger.logInfo(Arrays.toString(e.getStackTrace()));
                }
            }
        }
    }

    public static void zipFiles(String pathToZip, List<String> pathToFiles){
        ZipOutputStream zos = null;
        try{
            Logger.logInfo("Добавление к архиву игрового процесса \'" + pathToZip + "\'\n");
            zos = new ZipOutputStream(new FileOutputStream(pathToZip));
            for (var path : pathToFiles){
                Logger.logInfo("\t+ \'" + path + "\'\n");
                FileInputStream fis = new FileInputStream(path);
                ZipEntry entry = new ZipEntry(Paths.get(path).getFileName().toString());
                zos.putNextEntry(entry);
                zos.write(fis.readAllBytes());
                zos.closeEntry();
                fis.close();
            }
            Logger.logInfo("Удаление файлов вне архива\n");
            for (var path : pathToFiles){
                if (Files.deleteIfExists(Paths.get(path)))
                    Logger.logInfo("\t- \'" + path + "\' удален\n");
                else
                    Logger.logInfo("\t- Ошибка при удалении \'" + path + "\'\n");
            }
        } catch (IOException e) {
            Logger.logInfo(Arrays.toString(e.getStackTrace()));
        } finally {
            if (zos != null) {
                try {
                    zos.close();
                } catch (IOException e) {
                    Logger.logInfo(Arrays.toString(e.getStackTrace()));
                }
            }
        }
    }

    public static List<GameProgress> openZip(String pathToZip, String pathToFolder){
        ZipInputStream zis = null;
        ZipEntry entry;
        ArrayList<GameProgress> progress = new ArrayList<>();
        Logger.logInfo("Распаковка архива \'" + pathToZip + "\' в папку \'" + pathToFolder + "\'\n");
        try{
            zis = new ZipInputStream(new FileInputStream(pathToZip));
            while ((entry = zis.getNextEntry()) != null){
                Logger.logInfo("\t+ \'" + entry.getName() + "\'\n");
                String path = Paths.get(pathToFolder, entry.getName()).toString();
                FileOutputStream fos = new FileOutputStream(path);
                fos.write(zis.readAllBytes());
                zis.closeEntry();
                fos.close();
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path));
                progress.add((GameProgress)ois.readObject());
                ois.close();
            }
        } catch (IOException e) {
            Logger.logInfo(Arrays.toString(e.getStackTrace()));
        } catch (ClassNotFoundException e) {
            Logger.logInfo(Arrays.toString(e.getStackTrace()));
        } finally {
            if (zis != null) {
                try {
                    zis.close();
                } catch (IOException e) {
                    Logger.logInfo(Arrays.toString(e.getStackTrace()));
                }
            }
        }
        return progress;
    }
}