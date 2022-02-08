package com.ask0n;

import java.util.*;
import java.util.stream.Collectors;

public class Main {
    static Collection<Person> persons = new ArrayList<Person>();

    public static void main(String[] args) {
        generate();
        long countOfMinor = persons.stream().filter(x -> x.getAge() < 18).count();
        List<String> recruits = persons.stream()
                .filter(x -> x.getAge() >= 18 && x.getAge() <= 27)
                .map(Person::getFamily)
                .collect(Collectors.toList());
        List<Person> workable = persons.stream()
                .filter(x -> x.getAge() >= 18)
                .filter(x -> (x.getAge() <= 60 && x.getSex() == Sex.WOMEN) || (x.getAge() <= 65 && x.getSex() == Sex.MAN))
                .sorted(Comparator.comparing(Person::getFamily))
                .collect(Collectors.toList());

        System.out.println("Кол-во несовершеннолетних " + countOfMinor);
        System.out.println();
        recruits.forEach(System.out::println);
        System.out.println();
        workable.forEach(System.out::println);
    }

    public static void generate(){
        List<String> names = Arrays.asList("Jack", "Connor", "Harry", "George", "Samuel", "John");
        List<String> families = Arrays.asList("Evans", "Young", "Harris", "Wilson", "Davies", "Adamson", "Brown");
        for (int i = 0; i < 10000000; i++) {
            persons.add(new Person(
                    names.get(new Random().nextInt(names.size())),
                    families.get(new Random().nextInt(families.size())),
                    new Random().nextInt(100),
                    Sex.values()[new Random().nextInt(Sex.values().length)],
                    Education.values()[new Random().nextInt(Education.values().length)])
            );
        }
    }
}
