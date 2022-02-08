package com.ask0n.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@IdClass(PersonsId.class)
public class Persons {
    @Id
    private String name;
    @Id
    private String surname;
    @Id
    private int age;
    @Column(nullable = false)
    private String phoneNumber;
    @Column(nullable = false)
    private String cityOfLiving;
}
