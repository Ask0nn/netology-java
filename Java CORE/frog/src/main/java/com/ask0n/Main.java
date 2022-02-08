package com.ask0n;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final String commandList = "Список команд:\n" +
            "    +N - прыгни на N шагов направо\n" +
            "    -N - прыгни на N шагов налево\n" +
            "    << - Undo (отмени последнюю команду)\n" +
            "    >> - Redo (повтори отменённую команду)\n" +
            "    !! - повтори последнюю команду\n" +
            "    0 - выход\n";

    public static void main(String[] args) {
        Frog frog = new Frog();
        List<FrogCommand> commands = new ArrayList<>();
        int lastPosition;
        int curCommand = -1;
        label:
        while (true) {
            System.out.println(commandList);
            System.out.println("Введите команду из списка.");
            String command = read();
            lastPosition = frog.position;

            switch (command) {
                case "0":
                    paintField(frog, lastPosition);
                    break label;
                case "<<":
                    if (curCommand < 0) {
                        System.out.println("Нечего отменять!");
                    } else {
                        commands.get(curCommand).undo();
                        curCommand--;
                    }
                    break;
                case ">>":
                    if (curCommand == commands.size() - 1) {
                        System.out.println("Нет отмененной команды которую нужно повторить!");
                    } else {
                        curCommand++;
                        commands.get(curCommand).execute();
                    }
                    break;
                case "!!":
                    if (commands.isEmpty()) {
                        System.out.println("Нечего повторять!");
                    } else {
                        commands.get(curCommand).execute();
                    }
                    break;
                default:
                    if (curCommand != commands.size() - 1) {
                        commands.clear();
                        curCommand = -1;
                    }
                    int steps = Integer.parseInt(command);
                    FrogCommand cmd = steps < 0 ?
                            FrogCommands.jumpLeftCommand(frog, steps * -1) : FrogCommands.jumpRightCommand(frog, steps);
                    curCommand++;
                    commands.add(cmd);
                    cmd.execute();
                    break;
            }

            paintField(frog, lastPosition);
        }
    }

    public static String read(){
        Scanner in = new Scanner(System.in);
        return in.next();
    }

    private static void paintField(Frog frog, int lastPosition){
        System.out.println("F - лягушка, # - пустые клетки, f - прошлая позиция лягушки");
        for (int i = 1; i <= Frog.MAX_POSITION; i++){
            if (i == frog.position) System.out.print("\tF");
            else if (i == lastPosition) System.out.print("\tf");
            else System.out.print("\t#");
        }
        System.out.println();
    }
}
