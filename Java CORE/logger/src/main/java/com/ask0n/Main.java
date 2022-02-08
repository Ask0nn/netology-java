package com.ask0n;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {
    private static final Logger logger = Logger.getInstance();
    private static List<Integer> array;

    public static void main(String[] args) {
        logger.log("Запускаем программу");
        logger.log("Просим пользователя ввести входные данные для списка");
        System.out.println("Введите размер списка: ");
        int n = readValue();
        System.out.println("Введите верхнюю границу для значений: ");
        int m = readValue();
        logger.log("Создаём и наполняем список");
        Random random = new Random();
        array = new ArrayList<>();
        for (int i = 0; i < n; i++) array.add(random.nextInt(m));
        System.out.print("Вот случайный список: ");
        array.forEach(System.out::print);
        System.out.println();
        logger.log("Просим пользователя ввести входные данные для фильтрации");
        System.out.println("Введите порог для фильтра: ");
        int f = readValue();
        logger.log("Запускаем фильтрацию");
        Filter filter = new Filter(f);
        var filtered = filter.filterOut(array);
        logger.log("Выводим результат на экран");
        System.out.print("Отфильтрованный список: ");
        filtered.forEach(System.out::print);
        System.out.println();
        logger.log("Завершаем программу");
    }

    public static int readValue(){
        Scanner in = new Scanner(System.in);
        return in.nextInt();
    }
}
