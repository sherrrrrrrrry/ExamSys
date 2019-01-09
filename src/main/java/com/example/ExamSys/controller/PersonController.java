package com.example.ExamSys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ExamSys.dao.PersonRepository;
import com.example.ExamSys.domain.Person;

@RestController
@RequestMapping(value = "person")
public class PersonController {
	
	@Autowired
	private PersonRepository personRepository;
	
	@PostMapping(path = "addPerson")
	public void addPerson(Person person) {
		personRepository.save(person);
	}
	
	@DeleteMapping(path = "deletePerson")
    public void deletePerson(Long id) {
        personRepository.delete(id);
    }
}
