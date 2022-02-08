package com.ask0n.controllers;

import com.ask0n.models.Persons;
import com.ask0n.services.PersonsService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequestMapping("/persons/")
public class PersonsController {
    private final PersonsService personsService;

    public PersonsController(PersonsService personsService) {
        this.personsService = personsService;
    }

    @GetMapping("by-city")
    @Secured("WRITE")
    public List<Persons> getPersonByCity(@RequestParam String city) {
        return personsService.getPersonByCity(city);
    }

    @GetMapping("by-age-less")
    @RolesAllowed("READ")
    public List<Persons> getPersonsByAgeLess(@RequestParam int age) {
        return personsService.getPersonsByAgeLess(age);
    }

    @GetMapping("by-name-surname")
    @PreAuthorize("hasRole('WRITE') or hasRole('DELETE')")
    public List<Persons> getPersonsByAgeLess(@RequestParam String name, @RequestParam String surname) {
        return personsService.getPersonsByNameAndSurname(name, surname);
    }

    @GetMapping("auth")
    @PreAuthorize("#username == authentication.principal.username")
    public String auth(@RequestParam String username) {
        return username;
    }
}
