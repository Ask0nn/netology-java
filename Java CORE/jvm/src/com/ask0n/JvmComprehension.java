package com.ask0n;

public class JvmComprehension {

    public static void main(String[] args) {
        int i = 1;                      // 1
        Object o = new Object();        // 2
        Integer ii = 2;                 // 3
        printAll(o, i, ii);             // 4
        System.out.println("finished"); // 7
    }

    private static void printAll(Object o, int i, Integer ii) {
        Integer uselessVar = 700;                   // 5
        System.out.println(o.toString() + i + ii);  // 6
    }
}
/*
    JvmComprehension.class + System classes - Application ClassLoader загружает классы в Metaspace
    main(String[] args) - новый фрейм в Stack Memory
    1 - Bootstrap ClassLoader загружает int и создается в heap (i ссылается на int в heap)
    2 - Bootstrap ClassLoader загружает Object и создается в heap (o ссылается на Object в heap)
    3 - Application ClassLoader загружает Integer и создается в heap (ii ссылается на Integer в heap)
    4 - printAll(Object o, int i, Integer ii) - новый фрейм в Stack Memory
        o ссылается на Object в heap (который уже существует в heap)
        i ссылается на int в heap (который уже существует в heap)
        ii ссылается на Integer в heap (который уже существует в heap)
    5 - uselessVar ссылается на Integer который уже существует в heap
    6 - Создается новый фрейм для метода o.toString() после выполнения сразу удаляется из Stack Memory
            потом новый фрейм для System.out.println()
        После выполнения удаляются фреймы:
        1. System.out.println()
        2. printAll(Object o, int i, Integer ii);
    7 - новый фрейм для System.out.println()
    Удаление фреймов:
    1. System.out.println()
    2. main(String[] args)
    Сборщик мусора
    Все создаваемые объекты помещаются в Eden.
 */