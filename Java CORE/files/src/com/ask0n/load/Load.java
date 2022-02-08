package com.ask0n.load;

import com.ask0n.GameProgress;
import com.ask0n.Logger;

public class Load {
    public static void main(String[] args) {
        Logger.logInfo("Загрузка сохранений...\n");
        var progress = GameProgress.openZip("Games\\savegames\\saves.zip", "Games\\savegames\\");
        progress.forEach(x -> System.out.println(x.toString()));
        Logger.logInfo("Загрузка сохранений завершена\n");
        Logger.saveLog();
    }
}
