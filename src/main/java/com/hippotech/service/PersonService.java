package com.hippotech.service;


import com.hippotech.dao.PersonDAO;
import com.hippotech.dto.PersonDTO;
import com.hippotech.model.Person;

import java.util.ArrayList;

public class PersonService {
    private final PersonDAO dao;

    public PersonService() {
        dao = new PersonDAO();
    }

    public Person convertToPerson(PersonDTO dto) {
        Person person = new Person();
        person.setId(dto.getId());
        person.setName(dto.getName());
        person.setColor(dto.getColor());
        person.setRetired(dto.getRetired());
        return person;
    }

    public PersonDTO convertToDTO(Person person) {
        PersonDTO dto = new PersonDTO();
        dto.setId(person.getId());
        dto.setName(person.getName());
        dto.setColor(person.getColor());
        dto.setRetired(person.getRetired());
        return dto;
    }

    public Person getPersonByName(String name) {
        PersonDTO person = dao.get(name);
        return convertToPerson(person);
    }

    public Person getPersonByID(String ID) {
        PersonDTO person = dao.getByID(ID);
        return convertToPerson(person);
    }

    public void addPerson(Person person) {
        PersonDTO dto = convertToDTO(person);
        dao.add(dto);
    }

    public ArrayList<Person> getAllPeople() {
        ArrayList<PersonDTO> peopleDTOs = dao.getAll();
        ArrayList<Person> people = new ArrayList<>();
        for (PersonDTO person :
                peopleDTOs) {
            people.add(convertToPerson(person));
        }
        return people;
    }

    public ArrayList<String> getAllPeopleName() {
        return dao.getAllName();
    }

    public ArrayList<String> getDoingPeopleIdName() {
        return dao.getDoingIdName();
    }

    public ArrayList<Person> getRetiredPeople(int check) {
        ArrayList<PersonDTO> peopleDTOs = dao.getRetiredPerson(check);
        ArrayList<Person> people = new ArrayList<>();
        for (PersonDTO person :
                peopleDTOs) {
            people.add(convertToPerson(person));
        }
        return people;
    }

    public void updatePerson(Person person) {
        dao.update(convertToDTO(person));
    }

    public void deletePerson(Person person) {
        dao.delete(convertToDTO(person));
    }

}
