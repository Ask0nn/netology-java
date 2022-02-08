package com.ask0n.services;

import com.ask0n.exceptions.NotFoundException;
import com.ask0n.models.Persons;
import com.ask0n.repositories.PersonsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonsService {
    private final PersonsRepository personsRepository;

    public PersonsService(PersonsRepository personsRepository) {
        this.personsRepository = personsRepository;
    }

    public List<Persons> getPersonByCity(String city) {
        return personsRepository.findByCityOfLiving(city)
                .orElseThrow(NotFoundException::new);
    }

    public List<Persons> getPersonsByAgeLess(int age) {
        return personsRepository.findByAgeLessThanOrderByAge(age)
                .orElseThrow(NotFoundException::new);
    }

    public List<Persons> getPersonsByNameAndSurname(String name, String surname) {
        return personsRepository.findByNameAndSurname(name, surname)
                .orElseThrow(NotFoundException::new);
    }
}
