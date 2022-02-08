package com.ask0n;

import java.io.FileNotFoundException;

public class PersonBuilder {
    private String name;
    private String surname;
    private int age;
    private String address;

    public PersonBuilder setName(String name) {
        this.name = name;
        return this;
    }
    public PersonBuilder setSurname(String surname) {
        this.surname = surname;
        return this;
    }
    public PersonBuilder setAge(int age) {
        this.age = age;
        return this;
    }
    public PersonBuilder setAddress(String address) {
        this.address = address;
        return this;
    }

    public Person build() throws IllegalStateException, IllegalArgumentException {
        if (name == null || surname == null) throw new IllegalStateException("Не хватает обяхательных полей");
        if (age < 0) throw new IllegalArgumentException("Возраст не может быть отрицательным");

        return new Person(name, surname, age, address);
    }
}
