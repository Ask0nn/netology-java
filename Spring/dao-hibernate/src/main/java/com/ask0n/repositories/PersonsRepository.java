package com.ask0n.repositories;

import com.ask0n.models.Persons;
import com.ask0n.models.PersonsId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonsRepository extends CrudRepository<Persons, PersonsId> {
    Optional<List<Persons>> findByCityOfLiving(String city);
    Optional<List<Persons>> findByAgeLessThanOrderByAge(int age);
    Optional<List<Persons>> findByNameAndSurname(String name, String surname);
}
