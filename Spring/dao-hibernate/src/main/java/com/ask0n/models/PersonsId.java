package com.ask0n.models;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
public class PersonsId implements Serializable {
    private String name;
    private String surname;
    private int age;
}
