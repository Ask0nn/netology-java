package com.ask0n;

import com.opencsv.bean.CsvBindByPosition;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

@XmlRootElement(name = "employee")
@XmlAccessorType (XmlAccessType.FIELD)
public class Employee {
    @CsvBindByPosition(position = 0)
    public long id;
    @CsvBindByPosition(position = 1)
    public String firstName;
    @CsvBindByPosition(position = 2)
    public String lastName;
    @CsvBindByPosition(position = 3)
    public String country;
    @CsvBindByPosition(position = 4)
    public int age;

    public Employee() {}

    public Employee(long id, String firstName, String lastName, String country, int age) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.country = country;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Employee {" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", country='" + country + '\'' +
                ", age=" + age +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Employee)) return false;
        Employee employee = (Employee) o;
        return id == employee.id && age == employee.age && Objects.equals(firstName, employee.firstName) && Objects.equals(lastName, employee.lastName) && Objects.equals(country, employee.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, country, age);
    }
}
