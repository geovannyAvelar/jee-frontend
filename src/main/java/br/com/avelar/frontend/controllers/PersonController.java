package br.com.avelar.frontend.controllers;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.avelar.backend.rest.model.Person;
import br.com.avelar.frontend.bussiness.PersonLogic;

@ViewScoped
@Named
public class PersonController implements Serializable {
	
	private static final long serialVersionUID = 8328810995595502209L;

	private static final String THIS = "index.xhtml?faces-redirect=true";
	
	private Person person;
	
	private List<Person> peopleList;
	
	@Inject
	private PersonLogic logic;
	
	@PostConstruct
	public void init() {
		peopleList = logic.findAllPeople();
	}
	
	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}
	
	public List<Person> getPeopleList() {
		return peopleList;
	}

	public void setPeopleList(List<Person> peopleList) {
		this.peopleList = peopleList;
	}
	
	public String savePerson() {
		logic.savePerson(person);
		return THIS;
	}
	
	public String deletePerson() {
		logic.deletePerson(person);
		return THIS;
	}
	
}
