package com.ask0n.save;

import com.ask0n.GameProgress;
import com.ask0n.Logger;

import java.util.Arrays;

public class Save {
    public static void main(String[] args) {
        Logger.logInfo("Сохранение...\n");
        GameProgress g1 = new GameProgress(100, 3, 55, 45.24);
        GameProgress g2 = new GameProgress(25, 2, 25, 19.201);
        GameProgress g3 = new GameProgress(100, 10, 60, 100);
        String p1 = "Games\\savegames\\g1.txt";
        String p2 = "Games\\savegames\\g2.txt";
        String p3 = "Games\\savegames\\g3.txt";
        GameProgress.saveGame(p1, g1);
        GameProgress.saveGame(p2, g2);
        GameProgress.saveGame(p3, g3);
        GameProgress.zipFiles("Games\\savegames\\saves.zip", Arrays.asList(p1, p2, p3));
        Logger.logInfo("Сохранение завершено\n\n");
        Logger.saveLog();
    }
}
